defmodule DraconicSystems.CommentsTest do
  use DraconicSystems.DataCase

  import DraconicSystems.AccountsFixtures
  alias DraconicSystems.Accounts.User
  alias DraconicSystems.Comments

  describe "comments" do
    alias DraconicSystems.Comments.Comment

    import DraconicSystems.CommentsFixtures

    @invalid_attrs %{markup_text: nil}

    setup do
      :ok = Ecto.Adapters.SQL.Sandbox.checkout(Repo)
    end

    test "list_comments/0 returns all comments" do
      user = user_fixture()
      comment = comment_fixture(user)
      assert Comments.list_comments() == [comment]
    end

    test "get_comment!/1 returns the comment with given id" do
      user = user_fixture()
      comment = comment_fixture(user)
      assert Comments.get_comment!(comment.id) == comment
    end

    test "create_comment/1 with valid data creates a comment" do
      valid_attrs = %{markup_text: "some markup_text"}

      user = user_fixture()
      assert {:ok, %Comment{} = comment} = Comments.create_comment(user, valid_attrs)
      assert comment.markup_text == "some markup_text"
    end

    test "create_comment/1 with invalid data returns error changeset" do
      user = user_fixture()
      assert {:error, %Ecto.Changeset{}} = Comments.create_comment(user, @invalid_attrs)
    end

    test "update_comment/2 with valid data updates the comment" do
      user = user_fixture()
      comment = comment_fixture(user)
      update_attrs = %{markup_text: "some updated markup_text"}

      assert {:ok, %Comment{} = comment} = Comments.update_comment(comment, update_attrs)
      assert comment.markup_text == "some updated markup_text"
    end

    test "update_comment/2 with invalid data returns error changeset" do
      user = user_fixture()
      comment = comment_fixture(user)
      assert {:error, %Ecto.Changeset{}} = Comments.update_comment(comment, @invalid_attrs)
      assert comment == Comments.get_comment!(comment.id)
    end

    test "delete_comment/1 deletes the comment" do
      user = user_fixture()
      comment = comment_fixture(user)
      assert {:ok, %Comment{}} = Comments.delete_comment(comment)
      assert_raise Ecto.NoResultsError, fn -> Comments.get_comment!(comment.id) end
    end

    test "change_comment/1 returns a comment changeset" do
      user = user_fixture()
      comment = comment_fixture(user)
      assert %Ecto.Changeset{} = Comments.change_comment(comment)
    end
  end
end
