defmodule DraconicSystemsWeb.PageController do
  use DraconicSystemsWeb, :controller

  def home(conn, _params) do
    render(conn, :home)
  end
end
