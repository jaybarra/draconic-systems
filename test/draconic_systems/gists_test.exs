defmodule DraconicSystems.GistsTest do
  use DraconicSystems.DataCase

  import DraconicSystems.AccountsFixtures

  alias DraconicSystems.Gists

  describe "gists" do
    alias DraconicSystems.Gists.Gist

    import DraconicSystems.GistsFixtures

    @invalid_attrs %{name: nil, description: nil, markup_text: nil}

    setup do
      :ok = Ecto.Adapters.SQL.Sandbox.checkout(Repo)
    end

    test "list_gists/0 returns all gists" do
      user = user_fixture()
      gist = gist_fixture(user)
      assert Gists.list_gists() == [gist]
    end

    test "get_gist!/1 returns the gist with given id" do
      user = user_fixture()
      gist = gist_fixture(user)
      assert Gists.get_gist!(gist.id) == gist
    end

    test "create_gist/1 with valid data creates a gist" do
      valid_attrs = %{
        name: "some name",
        description: "some description",
        markup_text: "some markup_text"
      }

      user = user_fixture()
      assert {:ok, %Gist{} = gist} = Gists.create_gist(user, valid_attrs)
      assert gist.name == "some name"
      assert gist.description == "some description"
      assert gist.markup_text == "some markup_text"
    end

    test "create_gist/1 with invalid data returns error changeset" do
      user = user_fixture()
      assert {:error, %Ecto.Changeset{}} = Gists.create_gist(user, @invalid_attrs)
    end

    test "update_gist/2 with valid data updates the gist" do
      user = user_fixture()
      gist = gist_fixture(user)

      update_attrs = %{
        name: "some updated name",
        description: "some updated description",
        markup_text: "some updated markup_text"
      }

      assert {:ok, %Gist{} = gist} = Gists.update_gist(gist, update_attrs)
      assert gist.name == "some updated name"
      assert gist.description == "some updated description"
      assert gist.markup_text == "some updated markup_text"
    end

    test "update_gist/2 with invalid data returns error changeset" do
      user = user_fixture()
      gist = gist_fixture(user)
      assert {:error, %Ecto.Changeset{}} = Gists.update_gist(gist, @invalid_attrs)
      assert gist == Gists.get_gist!(gist.id)
    end

    test "delete_gist/1 deletes the gist" do
      user = user_fixture()
      gist = gist_fixture(user)
      assert {:ok, %Gist{}} = Gists.delete_gist(gist)
      assert_raise Ecto.NoResultsError, fn -> Gists.get_gist!(gist.id) end
    end

    test "change_gist/1 returns a gist changeset" do
      user = user_fixture()
      gist = gist_fixture(user)
      assert %Ecto.Changeset{} = Gists.change_gist(gist)
    end
  end

  describe "saved_gists" do
    alias DraconicSystems.Gists.SavedGist

    import DraconicSystems.GistsFixtures

    @invalid_attrs %{}

    setup do
      :ok = Ecto.Adapters.SQL.Sandbox.checkout(Repo)
    end

    test "list_saved_gists/0 returns all saved_gists" do
      user = user_fixture()
      saved_gist = saved_gist_fixture(user)
      assert Gists.list_saved_gists() == [saved_gist]
    end

    test "get_saved_gist!/1 returns the saved_gist with given id" do
      user = user_fixture()
      saved_gist = saved_gist_fixture(user)
      assert Gists.get_saved_gist!(saved_gist.id) == saved_gist
    end

    test "create_saved_gist/1 with valid data creates a saved_gist" do
      valid_attrs = %{}

      user = user_fixture()
      assert {:ok, %SavedGist{} = saved_gist} = Gists.create_saved_gist(user, valid_attrs)
    end

    test "create_saved_gist/1 with invalid data returns error changeset" do
      assert {:error, %Ecto.Changeset{}} = Gists.create_saved_gist(@invalid_attrs)
    end

    test "update_saved_gist/2 with valid data updates the saved_gist" do
      user = user_fixture()
      saved_gist = saved_gist_fixture(user)
      update_attrs = %{}

      assert {:ok, %SavedGist{} = saved_gist} = Gists.update_saved_gist(saved_gist, update_attrs)
    end

    test "update_saved_gist/2 with invalid data returns error changeset" do
      user = user_fixture()
      saved_gist = saved_gist_fixture(user)
      assert {:error, %Ecto.Changeset{}} = Gists.update_saved_gist(saved_gist, @invalid_attrs)
      assert saved_gist == Gists.get_saved_gist!(saved_gist.id)
    end

    test "delete_saved_gist/1 deletes the saved_gist" do
      user = user_fixture()
      saved_gist = saved_gist_fixture(user)
      assert {:ok, %SavedGist{}} = Gists.delete_saved_gist(saved_gist)
      assert_raise Ecto.NoResultsError, fn -> Gists.get_saved_gist!(saved_gist.id) end
    end

    test "change_saved_gist/1 returns a saved_gist changeset" do
      user = user_fixture()
      saved_gist = saved_gist_fixture(user)
      assert %Ecto.Changeset{} = Gists.change_saved_gist(saved_gist)
    end
  end
end
