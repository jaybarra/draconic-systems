defmodule DraconicSystems.Repo do
  use Ecto.Repo,
    otp_app: :draconic_systems,
    adapter: Ecto.Adapters.Postgres
end
