defmodule DraconicSystems.Crawler do
  @moduledoc false

  use GenServer

  def init(urls) do
    {:ok, urls}
  end

  def start do
    GenServer.start(__MODULE__, nil)
  end

  def handle_call(:crawl, _from, %{url: url}) do
    candidates = crawl_url(url)
    {:reply, candidates, %{url: url}}
  end

  def extract_urls_from_html(html) do
    regex = ~r/href=["']([^"']*)["']/

    html
    |> Regex.scan(regex)
    |> Enum.map(fn [_, href] -> href end)
  end

  def crawl_url(url) do
    Finch.build(:get, url)
    |> Finch.request(DraconicSystems.FinchCrawler)
    |> case do
      {:ok, %Finch.Response{body: body}} ->
        body |> IO.inspect() |> extract_urls_from_html()

      {:error, _} ->
        IO.puts("Failed to crawl " <> url)
        []
    end
  end
end
