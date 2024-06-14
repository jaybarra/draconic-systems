defmodule DraconicSystems.UrlValidator do
  @accepted_schemes ["http://", "https://"]
  @ignorable_extensions [
    ".css",
    ".js",
    ".ico",
    ".png",
    ".jpg",
    ".jpeg",
    ".gif",
    ".svg",
    ".webp",
    ".mp4",
    ".mp3",
    ".wav",
    ".avi",
    ".mov",
    ".pdf",
    ".doc",
    ".docx",
    ".xls",
    ".xlsx",
    ".ppt",
    ".pptx",
    ".zip",
    ".rar",
    ".tar",
    ".gz",
    ".7z",
    ".bin",
    ".exe",
    ".dmg",
    ".iso",
    ".woff",
    ".woff2",
    ".ttf",
    ".otf",
    ".eot",
    ".swf",
    ".flv"
  ]
  @ignored_domains [
    # anything Amazon
    "a2z.com",
    "amazon-corp.com",
    "amazon.ca",
    "amazon.cn",
    "amazon.co.jp",
    "amazon.co.uk",
    "amazon.com",
    "amazon.com.au",
    "amazon.com.mx",
    "amazon.de",
    "amazon.es",
    "amazon.eu",
    "amazon.fr",
    "amazon.in",
    "amazon.it",
    "amazon.nl",
    "amazon.sa",
    "amazoncognito.com",
    "amazoncrl.com",
    "amazonlogistics.com",
    "amazonlogistics.eu",
    "amazonpay.com",
    "amazonpay.in",
    "amazonpayments.com",
    "amazontrust.com",
    "amzn.to",
    "associates-amazon.com",
    "images-amazon.com",
    "media-amazon.com",
    "ssl-images-amazon.com",
    # Google
    "fonts.googleapis.com"
  ]

  @online_shops [
    "alibaba.com",
    "aliexpress.com",
    "bestbuy.com",
    "bhphotovideo.com",
    "bloomingdales.com",
    "costco.com",
    "ebay.com",
    "etsy.com",
    "homedepot.com",
    "hsn.com",
    "ikea.com",
    "kohl's.com",
    "macys.com",
    "newegg.com",
    "nordstrom.com",
    "overstock.com",
    "qvc.com",
    "rakuten.com",
    "sears.com",
    "shopify.com",
    "target.com",
    "temu.com",
    "walmart.com",
    "wayfair.com",
    "wish.com",
    "zappos.com"
  ]
  @social_media [
    "facebook.com",
    "twitter.com",
    "instagram.com",
    "linkedin.com",
    "youtube.com",
    "tiktok.com",
    "pinterest.com",
    "snapchat.com",
    "reddit.com",
    "tumblr.com",
    "flickr.com",
    "wechat.com",
    "qq.com",
    "vk.com",
    "ok.ru",
    "douyin.com",
    "vimeo.com",
    "discord.com",
    "twitch.tv",
    "whatsapp.com"
  ]

  def usable_url?(url, _robots \\ nil) do
    case strip_query(url) do
      {:ok, base_url} ->
        valid_scheme?(base_url) and
          not ignoreable_extension?(base_url) and
          not ignored_domain?(base_url) and
          not online_shop?(base_url) and
          not social_media?(base_url)

      _ ->
        false
    end
  end

  def strip_query(url) do
    %URI{scheme: scheme, host: host, path: path} = URI.parse(url)

    if is_binary(scheme) and is_binary(host) and is_binary(path) do
      {:ok, "#{scheme}://#{host}#{path}"}
    else
      {:error, :invalid_url}
    end
  end

  defp valid_scheme?(url) do
    Enum.any?(@accepted_schemes, &String.starts_with?(url, &1))
  end

  defp ignoreable_extension?(url) do
    Enum.any?(@ignorable_extensions, &String.ends_with?(url, &1))
  end

  defp ignored_domain?(url) do
    domain = extract_domain(url)
    Enum.member?(@ignored_domains, domain)
  end

  defp online_shop?(url) do
    domain = extract_domain(url)
    Enum.member?(@online_shops, domain)
  end

  defp social_media?(url) do
    domain = extract_domain(url)
    Enum.member?(@social_media, domain)
  end

  defp extract_domain(url) do
    URI.parse(url).host
  end
end
