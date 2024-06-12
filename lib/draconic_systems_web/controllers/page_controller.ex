defmodule DraconicSystemsWeb.PageController do
  use DraconicSystemsWeb, :controller

  def home(conn, _params) do
    redirect(conn, to: "/create")
  end
end
