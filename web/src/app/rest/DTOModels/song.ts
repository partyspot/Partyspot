import { UUID } from "angular2-uuid";

export class Song {
    id: UUID;
    name: string;
    spotifyUri: string;
    genre: string;
}