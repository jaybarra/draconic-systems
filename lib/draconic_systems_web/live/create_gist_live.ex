defmodule DraconicSystemsWeb.CreateGistLive do
  use DraconicSystemsWeb, :live_view
  alias DraconicSystemsWeb.GistFormComponent
  alias DraconicSystems.{Gists, Gists.Gist}

  def mount(_params, _session, socket) do
    {:ok, socket}
  end

  def render(assigns) do
    ~H"""
    <div class="flex flex-col items-center justify-center">
      <h1 class="text-dsDark font-brand font-bold text-3xl">
        Have a code snippet to share?
      </h1>

      <div class="w-full px-28">
        <input
          type="text"
          class="py-2 w-full mt-8 text-sm text-dsLight rounded-lg border-white focus:ring-0 focus:outline-none bg-dsDark placeholder-dsLight-dark font-brand font-regular focus:border-dsPurple"
          placeholder="Search for gists..."
        />
      </div>
    </div>
    <.live_component
      module={GistFormComponent}
      id={:new}
      current_user={@current_user}
      form={to_form(Gists.change_gist(%Gist{}))}
    />
    """
  end
end
