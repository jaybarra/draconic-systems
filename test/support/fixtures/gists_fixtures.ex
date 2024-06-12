defmodule DraconicSystems.GistsFixtures do
  @moduledoc """
  This module defines test helpers for creating
  entities via the `DraconicSystems.Gists` context.
  """

  alias DraconicSystems.Gists, as: Gists

  @doc """
  Generate a gist.
  """
  def gist_fixture(user, attrs \\ %{}) do
    {:ok, gist} =
      attrs
      |> Enum.into(%{
        description: "some description",
        markup_text: "some markup_text",
        name: "some name"
      })
      |> Gists.create_gist(user)

    gist
  end

  @doc """
  Generate a saved_gist.
  """
  def saved_gist_fixture(user, attrs \\ %{}) do
    {:ok, saved_gist} =
      attrs
      |> Enum.into(%{})
      |> Gists.create_saved_gist(user)

    saved_gist
  end
end
