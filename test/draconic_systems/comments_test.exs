defmodule DraconicSystems.CommentsTest do
  use DraconicSystems.DataCase

  import DraconicSystems.AccountsFixtures
  import DraconicSystems.GistsFixtures

  alias DraconicSystems.Comments

  describe "comments" do
    alias DraconicSystems.Comments.Comment

    import DraconicSystems.CommentsFixtures

    @invalid_attrs %{markup_text: nil}

    setup do
      :ok = Ecto.Adapters.SQL.Sandbox.checkout(Repo)
    end

    test "list_comments/0 returns all comments" do
      with user <- user_fixture(),
           gist <- gist_fixture(user) do
        comment = comment_fixture(user, gist)
        assert Comments.list_comments() == [comment]
      end
    end

    test "get_comment!/1 returns the comment with given id" do
      with user <- user_fixture(),
           gist <- gist_fixture(user) do
        comment = comment_fixture(user, gist)
        assert Comments.get_comment!(comment.id) == comment
      end
    end

    test "create_comment/1 with valid data creates a comment" do
      valid_attrs = %{markup_text: "some markup_text"}

      with user <- user_fixture(),
           gist <- gist_fixture(user) do
        assert {:ok, %Comment{} = comment} = Comments.create_comment(user, gist, valid_attrs)
        assert comment.markup_text == "some markup_text"
      end
    end

    test "create_comment/1 with invalid data returns error changeset" do
      with user <- user_fixture(),
           gist <- gist_fixture(user) do
        assert {:error, %Ecto.Changeset{}} = Comments.create_comment(user, gist, @invalid_attrs)
      end
    end

    test "update_comment/2 with valid data updates the comment" do
      with user <- user_fixture(),
           gist <- gist_fixture(user) do
        comment = comment_fixture(user, gist)
        update_attrs = %{markup_text: "some updated markup_text"}

        assert {:ok, %Comment{} = comment} = Comments.update_comment(comment, update_attrs)
        assert comment.markup_text == "some updated markup_text"
      end
    end

    test "update_comment/2 with invalid data returns error changeset" do
      with user <- user_fixture(),
           gist <- gist_fixture(user) do
        comment = comment_fixture(user, gist)
        assert {:error, %Ecto.Changeset{}} = Comments.update_comment(comment, @invalid_attrs)
        assert comment == Comments.get_comment!(comment.id)
      end
    end

    test "delete_comment/1 deletes the comment" do
      with user <- user_fixture(),
           gist <- gist_fixture(user) do
        comment = comment_fixture(user, gist)
        assert {:ok, %Comment{}} = Comments.delete_comment(comment)
        assert_raise Ecto.NoResultsError, fn -> Comments.get_comment!(comment.id) end
      end
    end

    test "change_comment/1 returns a comment changeset" do
      with user <- user_fixture(),
           gist <- gist_fixture(user) do
        comment = comment_fixture(user, gist)
        assert %Ecto.Changeset{} = Comments.change_comment(comment)
      end
    end
  end
end
