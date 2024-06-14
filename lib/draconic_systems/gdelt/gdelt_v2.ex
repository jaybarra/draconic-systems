defmodule DraconicSystems.Gdelt.GdeltV2 do
  @moduledoc """
  Handles GDelt 2.0 interactions.

  @master_csv_data_file_list_english "http://data.gdeltproject.org/gdeltv2/masterfilelist.txt"
  @master_csv_data_file_list_translingual "http://data.gdeltproject.org/gdeltv2/masterfilelist-translation.txt"
  @latest_csv_data_file_list_english "http://data.gdeltproject.org/gdeltv2/lastupdate.txt"
  @latest_csv_data_file_list_translingual "http://data.gdeltproject.org/gdeltv2/lastupdate-translation.txt"
  """

  @data_root_url "http://data.gdeltproject.org/gdeltv2"

  @exports_suffix ".export.CSV.zip"
  # @mentions_suffix ".mentions.CSV.zip"
  # @gkg_suffix ".gkg.csv.zip"

  def fetch_for_time(datetime_str) do
    case nearest_gdelt_release(datetime_str) do
      {:ok, gdelt_time} ->
        case Req.get("#{@data_root_url}/#{gdelt_time}#{@exports_suffix}") do
          {:ok, response} ->
            with {_file_name, tsv_data} <- response.body |> List.first() do
              tsv_data
              |> String.split("\n")
              |> Stream.map(& &1)
              |> CSV.Decoding.Parser.parse(separator: ?\t)
              |> Enum.take(1)
              |> IO.inspect()
            end

          {:error, _} ->
            nil
        end

      {:error, reason} ->
        IO.puts(reason)
    end
  end

  defp nearest_gdelt_release(datetime_str) do
    with {:ok, datetime} <- Timex.parse(datetime_str, "{YYYY}{0M}{0D}{0h24}{0m}{0s}"),
         {:ok, rounded} <- round_down_to_nearest_15(datetime) do
      {:ok, Timex.format!(rounded, "{YYYY}{0M}{0D}{0h24}{0m}{0s}")}
    else
      _ -> {:error, "Invalid date-time string"}
    end
  end

  defp round_down_to_nearest_15(datetime) do
    minute = datetime.minute
    rounded_minute = div(minute, 15) * 15
    new_datetime = Timex.set(datetime, minute: rounded_minute, second: 0)
    {:ok, new_datetime}
  end
end
