defmodule DraconicSystems.GistsTest do
  use DraconicSystems.DataCase

  import DraconicSystems.AccountsFixtures

  alias DraconicSystems.Gists

  describe "gists" do
    alias DraconicSystems.Gists.Gist

    import DraconicSystems.GistsFixtures

    @invalid_attrs %{"name" => nil, "description" => nil, "markup_text" => nil}

    setup do
      :ok = Ecto.Adapters.SQL.Sandbox.checkout(Repo)
    end

    test "list_gists/0 returns all gists" do
      gist = user_fixture() |> gist_fixture()
      assert Gists.list_gists() == [gist]
    end

    test "get_gist!/1 returns the gist with given id" do
      gist = user_fixture() |> gist_fixture()
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
        "name" => "some updated name",
        "description" => "some updated description",
        "markup_text" => "some updated markup_text"
      }

      assert {:ok, %Gist{} = gist} = Gists.update_gist(user, Map.put(update_attrs, "id", gist.id))
      assert gist.name == "some updated name"
      assert gist.description == "some updated description"
      assert gist.markup_text == "some updated markup_text"
    end

    test "update_gist/2 with invalid data returns error changeset" do
      user = user_fixture()
      gist = gist_fixture(user)

      assert {:error, %Ecto.Changeset{}} =
               Gists.update_gist(user, @invalid_attrs |> Map.put("id", gist.id))

      assert gist == Gists.get_gist!(gist.id)
    end

    test "delete_gist/1 deletes the gist" do
      user = user_fixture()
      gist = gist_fixture(user)
      assert {:ok, _gist} = Gists.delete_gist(user, gist)
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

    setup do
      :ok = Ecto.Adapters.SQL.Sandbox.checkout(Repo)
    end

    test "list_saved_gists/0 returns all saved_gists" do
      with author <- user_fixture(),
           user <- user_fixture(),
           gist <- gist_fixture(author) do
        saved_gist = saved_gist_fixture(user, gist)
        assert Gists.list_saved_gists() == [saved_gist]
      end
    end

    test "get_saved_gist!/1 returns the saved_gist with given id" do
      with author <- user_fixture(),
           user <- user_fixture(),
           gist <- gist_fixture(author) do
        saved_gist = saved_gist_fixture(user, gist)
        assert Gists.get_saved_gist!(saved_gist.id) == saved_gist
      end
    end

    test "delete_saved_gist/1 deletes the saved_gist" do
      with author <- user_fixture(%{email: "author@example"}),
           user <- user_fixture(%{email: "user@tmp"}),
           gist <- gist_fixture(author) do
        saved_gist = saved_gist_fixture(user, gist)

        assert {:ok, %SavedGist{}} = Gists.delete_saved_gist(user, saved_gist)
        assert_raise Ecto.NoResultsError, fn -> Gists.get_saved_gist!(saved_gist.id) end
      end
    end
  end
end
