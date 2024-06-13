defmodule DraconicSystemsWeb.GistLive do
  use DraconicSystemsWeb, :live_view
  alias DraconicSystems.Accounts
  alias DraconicSystems.Gists
  alias DraconicSystemsWeb.GistFormComponent

  def mount(%{"id" => id}, _session, socket) do
    gist = Gists.get_gist!(id)
    author = Accounts.get_user!(gist.user_id)

    {:ok, relative_time} = Timex.format(gist.updated_at, "{relative}", :relative)

    enhanced_gist =
      gist
      |> Map.put(:relative, relative_time)
      |> Map.put(:author_email, author.email)

    {:ok, assign(socket, gist: enhanced_gist)}
  end

  def handle_event("delete", %{"id" => id}, socket) do
    case Gists.delete_gist(socket.assigns.current_user, id) do
      {:ok, _gist} ->
        socket = put_flash(socket, :info, "Gist Deleted")
        {:noreply, push_navigate(socket, to: ~p"/create")}

      {:error, reason} ->
        socket = put_flash(socket, :error, reason)
        {:noreply, socket}
    end
  end
end
