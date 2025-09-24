import {useState} from "react";
import ScrollButton from "./buttons/ScrollButton.tsx";
import Profile from "./Profile/Profile.tsx";
import emptyProfile from "../assets/empty_profile.png";
import axios from "axios";

export default function VideoScreen() {
    const [videoId, setVideoId] = useState<number | null>(null);
    const [user, setUser] = useState<string>("")

    const fetchNextVideo = async () => {
        try {
            const res = await axios.get("http://localhost:8080/video/next", {
                withCredentials: true,
            })
            setVideoId(res.data.id);
            setUser(res.data.creatorId)
        } catch (err) {
            console.error("Failed to fetch video:", err);
        }
    };

    return (
        <>
            {
                videoId ? (
                    <>
                        <div className="absolute bottom-4 left-4">
                            <Profile name={user} imageSrc={emptyProfile}/>
                        </div>
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
                className="h-16 w-16 absolute -right-20 mb-20"
            />
            <ScrollButton
                isUp={false}
                onClick={fetchNextVideo}
                className="h-16 w-16 absolute -right-20 mt-20"
            />
        </>
    )
}
