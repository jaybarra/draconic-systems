defmodule DraconicSystems.Crawler do
  @moduledoc false
  use GenServer

  alias DraconicSystems.UrlValidator

  def init(urls) do
    {:ok, urls}
  end

  def handle_call(:crawl, _from, %{urls: urls}) do
    candidates = crawl(urls)
    {:reply, candidates, %{urls: urls}}
  end

  def handle_cast({:push, urls}, state) do
    next_state = [urls | state]
    {:noreply, next_state}
  end

  defp extract_urls(%Req.Response{} = response, base_url) do
    content_type =
      response
      |> Req.Response.get_header("content-type")
      |> List.first()
      |> String.split(";")
      |> List.first()

    case content_type do
      "text/html" ->
        extract_urls_from_html(response.body, base_url)

      "application/xml" ->
        extract_urls_from_xml(response.body, base_url)

      _ ->
        IO.puts(content_type)
        dbg(content_type)
        []
    end
  end

  defp extract_urls_from_html(html, base_url) do
    regex = ~r/href=["']([^"']*)["']/

    Regex.scan(regex, html)
    |> Enum.map(&hd(tl(&1)))
    |> Enum.filter(&UrlValidator.usable_url?/1)
    |> Enum.reject(&(&1 == base_url))
    |> Enum.uniq()
  end

  defp extract_urls_from_xml(xml, base_url) do
    []
    |> Enum.reject(&(&1 == base_url))
  end

  def crawl(urls) when is_list(urls), do: urls |> Enum.flat_map(&crawl/1)
  def crawl(nil), do: []

  def crawl(url) do
    case Req.get(url, redirect: true) do
      {:ok, resp} -> extract_urls(resp, url)
      {:error, _} -> []
    end
  end
end
