defmodule DraconicSystems.Crawler do
  @moduledoc false

  def extract_urls_from_html(html) do
    []
  end

  def crawl_url(url) do
    next_urls =
      Finch.build(:get, url)
      |> Finch.request(DraconicSystems.FinchCrawler)
      |> case do
        {:ok, %Finch.Response{body: body}} ->
          body |> IO.inspect() |> extract_urls_from_html()

        {:error, _} ->
          IO.puts("Failed to crawl " <> url)
          []
      end

    {:ok, next_urls}
  end
end
