defmodule DraconicSystems.CommentsFixtures do
  @moduledoc """
  This module defines test helpers for creating
  entities via the `DraconicSystems.Comments` context.
  """

  alias DraconicSystems.Comments, as: Comments

  @doc """
  Generate a comment.
  """
  def comment_fixture(user, gist, attrs \\ %{}) do
    {:ok, comment} =
      attrs
      |> Enum.into(%{markup_text: "some markup_text"})
      |> then(fn c -> Comments.create_comment(user, gist, c) end)

    comment
  end
end
