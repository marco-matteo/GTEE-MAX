import {useState} from "react";
import ScrollButton from "./buttons/ScrollButton.tsx";
import Profile from "./Profile/Profile.tsx";
import emptyProfile from "../assets/empty_profile.png";
import axios from "axios";
import FavoriteButton from "./buttons/FavoriteButton.tsx";


export default function VideoScreen() {
    const [videoId, setVideoId] = useState<number | undefined>(undefined);
    const [user, setUser] = useState<string>("")
    const [caption, setCaption] = useState("")
    const [favorite, setFavorite] = useState(false);

    const fetchIsFavorite = async (id: number) => {
        if (!id) {
            setFavorite(false);
            return;
        }
        try {
            const res = await axios.get(`http://localhost:8080/video/favorite/${id}`, {
                withCredentials: true,
            });
            setFavorite(res.data);
        } catch {
            setFavorite(false);
        }
    }

    const clickFavorite = async () => {
        if (!videoId) {
            console.error("Failed to (un-)favorite video")
            return;
        }
        try {
            if (favorite) {
                await axios.delete("http://localhost:8080/video/favorite", {
                    withCredentials: true,
                });
            } else {
                await axios.post(`http://localhost:8080/video/favorite/${videoId}`, null, {
                    withCredentials: true,
                });
            }
            setFavorite(!favorite);
        } catch {
            alert(`Failed to ${favorite ? "un" : ""}favorite video`)
        }

    }

    const fetchNextVideo = async () => {
        try {
            const res = await axios.get("http://localhost:8080/video/next", {
                withCredentials: true,
            })
            setVideoId(res.data.id);
            setUser(res.data.creatorId)
            setCaption(res.data.caption)
            await fetchIsFavorite(res.data.id);
        } catch (err) {
            console.error("Failed to fetch video:", err);
        }
    };

    return (
        <>
            {
                videoId ? (
                    <>
                        <Profile name={user} caption={caption} imageSrc={emptyProfile}/>
                        <video
                            key={videoId}
                            autoPlay
                            loop
                            playsInline
                            className="w-full h-full rounded-xl bg-white border border-white"
                        >
                            <source src={`http://localhost:8080/video/stream/${videoId}`} type="video/mp4"/>
                        </video>
                    </>
                ) : (
                    <h1>Start Scrolling</h1>
                )
            }
            <ScrollButton
                isUp={true}
                onClick={fetchNextVideo}
                className="h-16 w-16 absolute -right-20 mb-40"
            />
            <FavoriteButton
                active={favorite}
                onClick={clickFavorite}
                className="h-16 w-16 absolute -right-20"
            />
            <ScrollButton
                isUp={false}
                onClick={fetchNextVideo}
                className="h-16 w-16 absolute -right-20 mt-40"
            />
        </>
    )
}
