defmodule DraconicSystemsWeb.AllGistsLive do
  use DraconicSystemsWeb, :live_view
  alias DraconicSystems.Gists

  def mount(_params, _session, socket) do
    {:ok, socket}
  end

  def ensure_current_user(socket) do
    IO.inspect(socket)

    if Map.has_key?(socket.assigns, :current_user) do
      socket
    else
      assign(socket, :current_user, nil)
    end
  end

  def handle_params(_unsigned_params, _uri, socket) do
    gists = Gists.list_gists()

    socket =
      socket
      |> ensure_current_user
      |> assign(:gists, gists)

    {:noreply, socket}
  end

  def gist(assigns) do
    ~H"""
    <div class="flex flex-col text-white items-center justify-center w-[48rem] mx-auto">
      <div class="font-brand font-normal text-xs">
        <div class="">
          <span class="text-sm font-bold">⟫</span><%= @gist.description %>
        </div>
        <div class="">
          <%= @gist.name %>
        </div>
      </div>
      <div id={@gist.id <> "-gist-wrapper"} class="flex w-full">
        <textarea id="syntax-numbers" class="syntax-numbers rounded-bl-md" readonly></textarea>
        <div
          id={@gist.id <> "-highlight-gist"}
          phx-hook="Highlight"
          class="syntax-area w-full rounded-br-md"
          data-name={@gist.name}
        >
          <pre><code class="language-elixir"><%= @gist.markup_text %></code></pre>
        </div>
      </div>
    </div>
    """
  end
end
