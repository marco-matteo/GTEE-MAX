import VideoScreen from "./components/VideoScreen.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import LoginBox from "./components/auth/LoginBox.tsx";
import VideoUploadScreen from "./components/VideoUploadScreen.tsx";
import RegisterBox from "./components/auth/RegisterBox.tsx";
import logoutIcon from "./assets/logout.png"

export default function App() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);
    const [uploadBoxOpen, setUploadBoxOpen] = useState(false);
    const [registerBoxOpen, setRegisterBoxOpen] = useState<boolean>(false)

    useEffect(() => {
        axios.get("http://localhost:8080/auth", {withCredentials: true})
            .then(() => setIsAuthenticated(true))
            .catch(() => setIsAuthenticated(false));
    }, [])

    const handleLogout = () => {
        setIsAuthenticated(false);
        document.cookie = "jwt="
    }

    if (isAuthenticated === null) {
        return <div className="text-white">Loading...</div>
    }

    return (
        <div className="min-h-screen w-full flex justify-center items-center bg-[#050505] relative">
            <div className="flex flex-col gap-4 items-center">
                {isAuthenticated ? (
                    <>
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
                    </>
                ) : registerBoxOpen ? (
                    <RegisterBox onRegisterSuccess={() => setRegisterBoxOpen(false)}
                                 onCancelClick={() => setRegisterBoxOpen(false)}/>
                ) : (
                    <LoginBox onLoginSuccess={() => setIsAuthenticated(true)}
                              onRegisterClick={() => setRegisterBoxOpen(true)}/>
                )

                }
            </div>
        </div>
    );
}
