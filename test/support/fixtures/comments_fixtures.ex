defmodule DraconicSystems.CommentsFixtures do
  @moduledoc """
  This module defines test helpers for creating
  entities via the `DraconicSystems.Comments` context.
  """

  alias DraconicSystems.Comments, as: Comments

  @doc """
  Generate a comment.
  """
  def comment_fixture(user, attrs \\ %{}) do
    {:ok, comment} =
      attrs
      |> Enum.into(%{
        markup_text: "some markup_text"
      })
      |> Comments.create_comment(user)

    comment
  end
end
