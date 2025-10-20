type User = {
    username: string,
    views: number,
    favorite_id: number,
}

type Video = {
    id: number,
    caption: string,
    views: number,
    creatorId: string,
}

export type {User, Video}