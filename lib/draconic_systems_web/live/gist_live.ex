defmodule DraconicSystemsWeb.GistLive do
  use DraconicSystemsWeb, :live_view
  alias DraconicSystems.Gists

  def mount(%{"id" => id}, _session, socket) do
    gist = Gists.get_gist!(id)
    {:ok, assign(socket, gist: gist)}
  end
end
