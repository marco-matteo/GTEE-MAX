import { useNavigate, useParams } from "react-router"
import { useEffect, useState } from "react"
import defaultProfile from "../assets/empty_profile.png"
import axios from "axios"
import type { User, Video } from "../types.ts"

export default function ProfileScreen() {
    const { username } = useParams<{ username: string }>()
    const navigate = useNavigate()
    const [user, setUser] = useState<User | null>(null)
    const [videos, setVideos] = useState<Video[]>([])
    const [videoSources, setVideoSources] = useState<Record<string, string>>({})
    const [loading, setLoading] = useState<boolean>(true)

    useEffect(() => {
        if (!username) return

        setLoading(true)

        axios.get(`http://localhost:8080/auth/user/check/${username}`)
            .then(() => {
                return Promise.all([
                    axios.get(`http://localhost:8080/user/${username}`, { withCredentials: true }),
                    axios.get(`http://localhost:8080/video/user/${username}`, { withCredentials: true })
                ])
            })
            .then(async ([userRes, videoRes]) => {
                setUser({
                    username: userRes.data.username,
                    views: userRes.data.views,
                    favorite_id: userRes.data.favorite_id
                })
                setVideos(videoRes.data)

                const sourceMap: Record<string, string> = {}
                await Promise.all(videoRes.data.map(async (video: Video) => {
                    const res = await axios.get(
                        `http://localhost:8080/video/stream/${video.id}`,
                        { withCredentials: true, responseType: "blob" }
                    )
                    sourceMap[video.id] = URL.createObjectURL(res.data)
                }))

                setVideoSources(sourceMap)
            })
            .catch((err) => {
                if (err.response && err.response.status === 404) {
                    navigate("/404")
                } else {
                    console.error("Error loading user or videos", err)
                }
            })
            .finally(() => setLoading(false))
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

            <div className="grid justify-center items-center grid-cols-4 px-96 gap-8">
                {videos.length > 0 ? (
                    videos.map((video) => (
                        <div key={video.id} className="">
                            {videoSources[video.id] ? (
                                <video
                                    src={videoSources[video.id]}
                                    className="h-96 aspect-video object-cover cursor-pointer hover:scale-105 transition-transform duration-150 rounded-3xl"
                                    onClick={() => {
                                        navigate(`/video/${video.id}`)
                                    }}
                                />
                            ) : (
                                <div className="">
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
