import logoutIcon from "../assets/logout.png"
import VideoScreen from "./VideoScreen.tsx";
import VideoUploadScreen from "./VideoUploadScreen.tsx";
import React from "react";

export default function Home({ uploadBoxOpen, setUploadBoxOpen, handleLogout }: { uploadBoxOpen: boolean, setUploadBoxOpen: React.Dispatch<React.SetStateAction<boolean>>, handleLogout: () => void}) {
    return (
        <div className="min-h-screen w-full flex justify-center items-center bg-[#050505] relative">
            <div className="flex flex-col gap-4 items-center">
                <button onClick={handleLogout} className="absolute top-5 left-5">
                    <img src={logoutIcon} alt="Logout Icon"/>
                </button>
                <div
                    className="relative h-screen aspect-[9/16] bg-white rounded-xl shadow-lg flex flex-col justify-center items-center">
                    <VideoScreen/>
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