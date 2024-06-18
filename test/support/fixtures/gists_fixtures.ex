defmodule DraconicSystems.GistsFixtures do
  @moduledoc """
  This module defines test helpers for creating
  entities via the `DraconicSystems.Gists` context.
  """

  alias DraconicSystems.Gists.Gist
  alias DraconicSystems.Accounts.User
  alias DraconicSystems.Gists, as: Gists

  @doc """
  Generate a gist.
  """
  def gist_fixture(%User{} = author, attrs \\ %{}) do
    {:ok, gist} =
      attrs
      |> Enum.into(%{
        description: "some description",
        markup_text: "some markup_text",
        name: "some name"
      })
      |> then(fn g -> Gists.create_gist(author, g) end)

    gist
  end

  @doc """
  Generate a saved_gist.
  """
  def saved_gist_fixture(%User{} = user, %Gist{} = gist) do
    {:ok, saved_gist} = Gists.create_saved_gist(user, gist)
    saved_gist
  end
end
