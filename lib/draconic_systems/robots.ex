defmodule DraconicSystems.Robots do
  @moduledoc """
  Robots.txt fetching and handling. 
  """

  use Memoize

  @doc """
  Return the robots.txt from a site, if present.
  """
  def get_robots(url) do
    case derive_robots_from_url(url) do
      {:ok, robots_url} ->
        case Req.get(robots_url, redirect: true) do
          {:ok, robots} -> Map.get(robots, :body)
          _ -> nil
        end

      {:error, _reason} ->
        nil
    end
  end

  @spec parse(binary) :: map
  def parse(robots_txt) do
    tokens =
      robots_txt
      |> String.split("\n")
      |> Stream.map(&String.trim/1)
      |> Stream.filter(&(!String.starts_with?(&1, "#")))
      |> Stream.map(&tokenize/1)
      |> Stream.filter(&(&1 != :unknown))
      |> Enum.to_list()

    do_parse(tokens, {[], nil}, %{})
  end

  defp tokenize(line) do
    cond do
      result = Regex.run(~r/^allow:?\s(.+)$/i, line) -> {:allow, Enum.at(result, 1)}
      result = Regex.run(~r/^disallow:?\s(.+)$/i, line) -> {:disallow, Enum.at(result, 1)}
      result = Regex.run(~r/^user-agent:?\s(.+)$/i, line) -> {:user_agent, Enum.at(result, 1)}
      true -> :unknown
    end
  end

  defp do_parse([{:user_agent, agent} | tokens], {agents, nil}, accum) do
    do_parse(tokens, {[String.downcase(agent) | agents], nil}, accum)
  end

  # Finished parsing a set of rules, add rules to accum and reset rules to nil
  defp do_parse([{:user_agent, agent} | tokens], {agents, rules}, accum) do
    accum = Enum.reduce(agents, accum, &Map.put(&2, &1, rules))
    do_parse(tokens, {[agent], nil}, accum)
  end

  # Add an allowed field
  defp do_parse([{:allow, path} | tokens], {agents, rules}, accum) do
    rules = Map.put(rules || %{}, :allowed, [path | rules[:allowed] || []])
    do_parse(tokens, {agents, rules}, accum)
  end

  # Add a disallowed field
  defp do_parse([{:disallow, path} | tokens], {agents, rules}, accum) do
    rules = Map.put(rules || %{}, :disallowed, [path | rules[:disallowed] || []])
    do_parse(tokens, {agents, rules}, accum)
  end

  # End the parsing, current buffer has nothing, so just return the accumulator
  defp do_parse([], {_agents, nil}, accum) do
    accum
  end

  # End the parsing, and add the current buffer to the accumulator
  defp do_parse([], {agents, rules}, accum) do
    Enum.reduce(agents, accum, &Map.put(&2, &1, rules))
  end

  @doc """
  Return whether a URL is allowed based on the ROBOT.
  """
  def allowed?(robots, url) do
    {:ok, url}
  end

  defp derive_robots_from_url(url) do
    with %URI{scheme: scheme, host: host} <- URI.parse(url) do
      if is_binary(scheme) and is_binary(host) do
        {:ok, "#{scheme}://#{host}/robots.txt"}
      else
        {:error, :unable_to_parse}
      end
    end
  end
end
