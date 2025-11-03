import {useNavigate, useParams} from "react-router"
import {useEffect, useState} from "react"
import defaultProfile from "../assets/empty_profile.png"
import axios from "axios"
import type {User, Video} from "../types.ts"

export default function ProfileScreen() {
    const {username} = useParams<{ username: string }>()
    const navigate = useNavigate()
    const [user, setUser] = useState<User | null>(null)
    const [videos, setVideos] = useState<Video[]>([])
    const [videoSources, setVideoSources] = useState<Record<string, string>>({})
    const [favorite, setFavorite] = useState<Video | null>(null)
    const [favoriteSource, setFavoriteSource] = useState<string | null>(null)
    const [loading, setLoading] = useState<boolean>(true)

    const fetchVideoSource = async (videoId: number): Promise<string> => {
        const res = await axios.get(`http://localhost:8080/video/stream/${videoId}`, {
            withCredentials: true,
            responseType: "blob",
        })
        return URL.createObjectURL(res.data)
    }

    useEffect(() => {
        if (!username) return

        const fetchProfileData = async () => {
            setLoading(true)

            try {
                await axios.get(`http://localhost:8080/auth/user/check/${username}`)

                const [userRes, videosRes] = await Promise.all([
                    axios.get(`http://localhost:8080/user/${username}`, { withCredentials: true }),
                    axios.get(`http://localhost:8080/video/user/${username}`, { withCredentials: true }),
                ])

                const fetchedUser = {
                    username: userRes.data.username,
                    views: userRes.data.views,
                    favorite_id: userRes.data.favorite?.id ?? undefined,
                }
                setUser(fetchedUser)
                setVideos(videosRes.data)

                const videoBlobs = await Promise.all(
                    videosRes.data.map(async (video: Video) => {
                        try {
                            const src = await fetchVideoSource(video.id)
                            return [video.id, src] as const
                        } catch {
                            console.warn(`Failed to load video ${video.id}`)
                            return [video.id, ""] as const
                        }
                    })
                )

                setVideoSources(Object.fromEntries(videoBlobs))
                if (fetchedUser.favorite_id) {
                    try {
                        const favRes = await axios.get(
                            `http://localhost:8080/video/${fetchedUser.favorite_id}`,
                            { withCredentials: true }
                        )
                        setFavorite(favRes.data)

                        const favSrc = await fetchVideoSource(fetchedUser.favorite_id)
                        setFavoriteSource(favSrc)
                    } catch (err) {
                        console.error("Failed to load favorite video", err)
                    }
                }
            } catch {
                navigate("/404")
            } finally {
                setLoading(false)
            }
        }

        fetchProfileData().then(r => console.log(r))
    }, [username, navigate])

    if (loading) return <div className="h-screen w-full bg-black"></div>
    if (!user) return null

    return (
        <div className="min-h-screen w-full bg-black">
            <div className="flex items-center justify-center gap-10 h-96">
                {/* TODO: add profile pictures into db*/}
                <img src={defaultProfile} alt="Empty Profile" className="h-44" />
                <div className="relative flex flex-col justify-center items-center">
                    <h1 className="text-white text-5xl">@{user.username}</h1>
                    <p className="text-white absolute -bottom-10">views: {user.views}</p>
                </div>
            </div>
            {
                favorite && favoriteSource ? (
                    <div>
                        <div className="flex justify-center mb-24">
                            <video
                                src={favoriteSource}
                                className="h-96 aspect-[9/16] object-cover cursor-pointer hover:scale-105 transition-transform duration-150 rounded-3xl"
                                onClick={() => {
                                    navigate(`/video/${favorite?.id}`)
                                }}
                            />
                        </div>
                    </div>
                ) : ""
            }

            <div className="grid justify-center items-center grid-cols-4 px-96 gap-8">
                {videos.length > 0 ? (
                    videos.map((video) => (
                        <div key={video.id} >
                            {videoSources[video.id] ? (
                                <video
                                    src={videoSources[video.id]}
                                    className="h-96 aspect-[9/16] object-cover cursor-pointer hover:scale-105 transition-transform duration-150 rounded-3xl"
                                    onClick={() => {
                                        navigate(`/video/${video.id}`)
                                    }}
                                />
                            ) : (
                                <div>
                                    Loading video...
                                </div>
                            )}
                        </div>
                    ))
                ) : (
                    <p className="text-gray-400">No videos uploaded yet.</p>
                )}
            </div>
        </div>
    )
}
