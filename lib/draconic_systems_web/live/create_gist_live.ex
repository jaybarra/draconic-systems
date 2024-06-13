defmodule DraconicSystemsWeb.CreateGistLive do
  use DraconicSystemsWeb, :live_view
  alias DraconicSystemsWeb.GistFormComponent

  def mount(_params, _session, socket) do
    {:ok, socket}
  end
end
