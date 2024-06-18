defmodule DraconicSystems.Gists do
  @moduledoc """
  The Gists context.
  """

  import Ecto.Query, warn: false
  alias DraconicSystems.Accounts.User
  alias DraconicSystems.Repo

  alias DraconicSystems.Gists.Gist

  @doc """
  Returns the list of gists.

  ## Examples

      iex> list_gists()
      [%Gist{}, ...]

  """
  def list_gists do
    Repo.all(Gist)
  end

  @doc """
  Gets a single gist.

  Raises `Ecto.NoResultsError` if the Gist does not exist.

  ## Examples

      iex> get_gist!(123)
      %Gist{}

      iex> get_gist!(456)
      ** (Ecto.NoResultsError)

  """
  def get_gist!(id), do: Repo.get!(Gist, id)

  @doc """
  Creates a gist.

  ## Examples

      iex> create_gist(%{field: value})
      {:ok, %Gist{}}

      iex> create_gist(%{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def create_gist(%User{} = user, attrs \\ %{}) do
    user
    |> Ecto.build_assoc(:gists)
    |> Gist.changeset(attrs)
    |> Repo.insert()
  end

  @doc """
  Updates a gist.

  ## Examples

      iex> update_gist(user, %{field: new_value})
      {:ok, %Gist{}}

      iex> update_gist(user, %{field: bad_value})
      {:error, %Ecto.Changeset{}}
  """
  def update_gist(%User{} = user, attrs) do
    source_gist = Repo.get!(Gist, attrs["id"])

    if user.id == source_gist.user_id do
      source_gist
      |> Gist.changeset(attrs)
      |> Repo.update()
    else
      {:error, :unauthorized}
    end
  end

  @doc """
  Deletes a gist.

  ## Examples

      iex> delete_gist(user, gist)
      {:ok, %Gist{}}

      iex> delete_gist(user, gist)
      {:error, %Ecto.Changeset{}}

  """
  def delete_gist(%User{} = user, %Gist{id: id}), do: delete_gist(user, id)

  def delete_gist(%User{} = user, id) do
    gist = Repo.get(Gist, id)

    if user.id == gist.user_id do
      Repo.delete(gist)
      {:ok, gist}
    else
      {:error, :unauthorized}
    end
  end

  @doc """
  Returns an `%Ecto.Changeset{}` for tracking gist changes.

  ## Examples

      iex> change_gist(gist)
      %Ecto.Changeset{data: %Gist{}}

  """
  def change_gist(%Gist{} = gist, attrs \\ %{}) do
    Gist.changeset(gist, attrs)
  end

  alias DraconicSystems.Gists.SavedGist

  @doc """
  Returns the list of saved_gists.

  ## Examples

      iex> list_saved_gists()
      [%SavedGist{}, ...]

  """
  def list_saved_gists do
    Repo.all(SavedGist)
  end

  @doc """
  Gets a single saved_gist.

  Raises `Ecto.NoResultsError` if the Saved gist does not exist.

  ## Examples

      iex> get_saved_gist!(123)
      %SavedGist{}

      iex> get_saved_gist!(456)
      ** (Ecto.NoResultsError)

  """
  def get_saved_gist!(id), do: Repo.get!(SavedGist, id)

  @doc """
  Creates a saved_gist.

  ## Examples

      iex> create_saved_gist(%{field: value})
      {:ok, %SavedGist{}}

      iex> create_saved_gist(%{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def create_saved_gist(%User{} = user, %Gist{id: gist_id}) do
    user
    |> Ecto.build_assoc(:saved_gists)
    |> SavedGist.changeset(%{gist_id: gist_id})
    |> Repo.insert()
  end

  @doc """
  Deletes a saved_gist.

  ## Examples

      iex> delete_saved_gist(user, saved_gist)
      {:ok, %SavedGist{}}

      iex> delete_saved_gist(user, saved_gist)
      {:error, %Ecto.Changeset{}}

  """
  def delete_saved_gist(%User{} = user, %SavedGist{id: id}) do
    source_saved_gist = Repo.get!(SavedGist, id)

    if source_saved_gist.user_id == user.id do
      Repo.delete(source_saved_gist)
      {:ok, source_saved_gist}
    else
      {:error, :unauthorized}
    end
  end
end
