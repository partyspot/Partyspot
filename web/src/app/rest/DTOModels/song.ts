import { UUID } from "angular2-uuid";

export class Song {
    id: UUID;
    name: string;
    spotifyURI: string;
    genre: string;
}