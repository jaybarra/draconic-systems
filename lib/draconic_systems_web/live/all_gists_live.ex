defmodule DraconicSystemsWeb.AllGistsLive do
  use DraconicSystemsWeb, :live_view
  alias DraconicSystems.Gists

  def mount(_params, _session, socket) do
    {:ok, socket}
  end

  def handle_params(_unsigned_params, _uri, socket) do
    gists = Gists.list_gists()

    socket =
      assign(socket,
        gists: gists
      )

    {:noreply, socket}
  end

  def gist(assigns) do
    ~H"""
    <div class="flex flex-col text-white items-center justify-center w-[48rem] mx-auto h-[150px]">
      <div class="font-brand font-normal text-sm">
        <div class="">
          <%= @current_user.email %>
        </div>
        <div class="">
          <%= @gist.description %>
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
          <pre><code class="language-elixir">
        <%= @gist.markup_text %>
      </code></pre>
        </div>
      </div>
    </div>
    """
  end
end
