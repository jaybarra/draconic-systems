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

  @gdelt_events_headers [
    "GlobalEventID",
    "Day",
    "MonthYear",
    "Year",
    "FractionDate",
    "Actor1Code",
    "Actor1Name",
    "Actor1CountryCode",
    "Actor1KnownGroupCode",
    "Actor1EthnicCode",
    "Actor1Religion1Code",
    "Actor1Religion2Code",
    "Actor2Type1Code",
    "Actor2Type2Code",
    "Actor2Type3Code",
    "Actor2Code",
    "Actor2Name",
    "Actor2CountryCode",
    "Actor2KnownGroupCode",
    "Actor2EthnicCode",
    "Actor2Religion1Code",
    "Actor2Religion2Code",
    "Actor2Type1Code",
    "Actor2Type2Code",
    "Actor2Type3Code",
    "IsRootEvent",
    "EventCode",
    "EventBaseCode",
    "EventRootCode",
    "QuadClass",
    "GoldsteinScale",
    "NumMentions",
    "NumSources",
    "NumArticles",
    "AvgTone",
    "Actor1Geo_Type",
    "Actor1Geo_Fullname",
    "Actor1Geo_CountryCode",
    "Actor1Geo_ADM1Code",
    "Actor1Geo_ADM2Code",
    "Actor1Geo_Lat",
    "Actor1Geo_Long",
    "Actor1Geo_FeatureID",
    "Actor2Geo_Type",
    "Actor2Geo_Fullname",
    "Actor2Geo_CountryCode",
    "Actor2Geo_ADM1Code",
    "Actor2Geo_ADM2Code",
    "Actor2Geo_Lat",
    "Actor2Geo_Long",
    "Actor2Geo_FeatureID",
    "DATEADDED",
    "SOURCEURL"
  ]

  def fetch_for_time(datetime_str) do
    case nearest_gdelt_release(datetime_str) do
      {:ok, gdelt_time} ->
        case Req.get("#{@data_root_url}/#{gdelt_time}#{@exports_suffix}") do
          {:ok, response} ->
            with {_file_name, tsv_data} <- response.body |> List.first() do
              tsv_data
              |> CSV.Decoding.Decoder.decode(separator: ?\t, headers: @gdelt_events_headers)
              |> Enum.take(1)
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
