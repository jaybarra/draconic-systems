defmodule DraconicSystemsWeb.CreateGistLive do
  use DraconicSystemsWeb, :live_view
  alias DraconicSystemsWeb.GistFormComponent
  alias DraconicSystems.{Gists, Gists.Gist}

  def mount(_params, _session, socket) do
    {:ok, socket}
  end

  def render(assigns) do
    ~H"""
    <div class="flex items-center justify-center ds-gradient">
      <h1 class="text-white font-brand font-bold text-3xl">
        Instantly share code, notes and snippets
      </h1>
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
