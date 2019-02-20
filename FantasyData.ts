import axios from 'axios';
import config from '../config';

enum KeyName {
	NFLv3Stats = 'NFLV3STATS',
	NFLv3Projections = 'NFLV3PROJECTTIONS',
	NFLv3Scores = 'NFLV3SCORES',
	NFLv3ProjectionsDfsr = 'NFLV3PROJECTIONSDFSR'
}

enum EndPoint {
	scheduleUrl = '/v3/nfl/scores/JSON/Schedules/{season}',
	currentWeek = '/v3/nfl/scores/JSON/Schedules/CurrentWeek',
	currentSeason = '/v3/nfl/scores/JSON/Schedules/CurrentSeason'
}

interface QueryParams {
	[key: string]: number;
}

interface Stadium {
	StadiumID: number;
	Name: string;
	City: string;
	State: string;
	Country: string;
	Capacity: number;
	PlayingSurface: string;
	GeoLat: number;
	GeoLong: number;
	Type: string;
}

interface Schedule {
	GameKey: string;
	SeasonType: number;
	Season: number;
	Week: number;
	Date: string;
	AwayTeam: string;
	HomeTeam: string;
	Channel: string | null;
	PointSpread: number;
	OverUnder: number;
	StadiumID: number;
	Canceled: boolean;
	GeoLat: number | null;
	GeoLong: number | null;
	ForecastTempLow: number;
	ForecastTempHigh: number;
	ForecastDescription: string;
	ForecastWindChill: number;
	ForecastWindSpeed: number;
	AwayTeamMoneyLine: number;
	HomeTeamMoneyLine: number;
	Day: string;
	DateTime: string;
	GlobalGameID: number;
	GlobalAwayTeamID: number;
	GlobalHomeTeamID: number;
	ScoreID: number;
	StadiumDetails: Stadium;
	Status: string;
}

export type Schedules = ReadonlyArray<Schedule>;

class FantasyDataClient {
	static getStaticFantasyDataClient(keys: Map<string, string>): FantasyDataClient {
		return new FantasyDataClient(keys);
	}
	private keys: Map<string, string>;
	private endpoint: string;
	private constructor(keys: Map<string, string>) {
		this.keys = keys;
		this.endpoint = 'https://api.fantasydata.net';
	}
	// tslint:disable-next-line:no-any
	public async getPromise(scheduleUrl: EndPoint, keyName: KeyName, params?: QueryParams): Promise<any> {
		if (this.keys.size === 0 || !Object.values(EndPoint).includes(scheduleUrl)) {
			throw new Error(`API keys not valid OR endpoint not implemented.`);
		}
		await Promise.all([this.getEndpoint(scheduleUrl, params), this.getKey(keyName)]);
		const options = {
			headers: { 'Ocp-Apim-Subscription-Key': this.keys.get(keyName) },
			method: 'GET',
			url: this.endpoint
		};
		try {
			return await axios.get(this.endpoint, options);
		} catch (e) {
			throw new Error(`Axios call failed. Details: ${e.toString()}`);
		}
	}
	private async getKey(key: KeyName) {
		return this.keys.get(KeyName.NFLv3Stats);
	}

	private async getEndpoint(scheduleUrl: EndPoint, params?: QueryParams) {
		for (const property in params) {
			if (params.hasOwnProperty(property)) {
				this.endpoint = this.endpoint + EndPoint.scheduleUrl.replace(`{${property}}`, params[property].toString());
			}
		}
		return this.endpoint;
	}
}

const getFantasyDataClient = async (): Promise<FantasyDataClient> => {
	const keys: Map<KeyName, string> = new Map();
	if (config.FANTASYDATA_STATS_KEY) {
		keys.set(KeyName.NFLv3Stats, config.FANTASYDATA_STATS_KEY);
	}
	if (config.FANTASYDATA_PROJECTIONS_KEY) {
		keys.set(KeyName.NFLv3Projections, config.FANTASYDATA_PROJECTIONS_KEY);
	}
	if (keys.size === 0) {
		throw new Error(
			'Atleast one fantasy data subscription need to be configured. <FANTASYDATA_STATS_KEY, FANTASYDATA_PROJECTIONS_KEY>'
		);
	}
	return FantasyDataClient.getStaticFantasyDataClient(keys);
};

const getSchedule = async (seasonYear: number): Promise<Schedules> => {
	const fdClient = await getFantasyDataClient();
	const response = await fdClient.getPromise(EndPoint.scheduleUrl, KeyName.NFLv3Stats, { season: seasonYear });
	return response.data as Schedules;
};

const getCurrentWeek = async (): Promise<number> => {
	const fdClient = await getFantasyDataClient();
	const response = await fdClient.getPromise(EndPoint.currentWeek, KeyName.NFLv3Stats);
	return response.data;
};

const getCurrentSeason = async (): Promise<number> => {
	const fdClient = await getFantasyDataClient();
	const response = await fdClient.getPromise(EndPoint.currentSeason, KeyName.NFLv3Stats);
	return response.data;
};

export default { getFantasyDataClient, getSchedule, getCurrentWeek, getCurrentSeason };
