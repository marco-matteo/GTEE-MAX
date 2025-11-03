import logoutIcon from "../assets/logout.png"
import VideoUploadScreen from "./VideoUploadScreen.tsx";
import React from "react";
import ScrollButton from "./buttons/ScrollButton.tsx";
import {useNavigate} from "react-router";
import axios from "axios";

export default function Home({ uploadBoxOpen, setUploadBoxOpen, handleLogout }: { uploadBoxOpen: boolean, setUploadBoxOpen: React.Dispatch<React.SetStateAction<boolean>>, handleLogout: () => void}) {
    const navigate = useNavigate()

    const fetchNextVideo = async () => {
        try {
            const res = await axios.get("http://localhost:8080/video/next", {
                withCredentials: true,
            })
            return res.data.id
        } catch (err) {
            console.error("Failed to fetch video:", err);
        }
    };

    return (
        <div className="min-h-screen w-full flex justify-center items-center bg-[#050505] relative">
            <div className="flex flex-col gap-4 items-center">
                <button onClick={handleLogout} className="absolute top-5 left-5">
                    <img src={logoutIcon} alt="Logout Icon"/>
                </button>
                <div
                    className="relative h-screen aspect-[9/16] bg-white rounded-xl shadow-lg flex flex-col justify-center items-center">
                    Start Scrolling
                    <ScrollButton
                        isUp={false}
                        onClick={async () => {
                            const videoId = await fetchNextVideo()
                            navigate(`/video/${videoId}`)
                        }}
                        className="h-16 w-16 absolute -right-20 mt-40"
                    />
                </div>
                <button
                    onClick={() => setUploadBoxOpen(true)}
                    className="absolute right-5 top-5 bg-white p-4 rounded-full hover:scale-110 transition-transform duration-500">
                    Upload Video
                </button>
                {uploadBoxOpen && <VideoUploadScreen onClose={() => setUploadBoxOpen(false)}/>}

            </div>
        </div>
    )
}