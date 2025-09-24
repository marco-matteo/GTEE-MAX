import VideoScreen from "./components/VideoScreen.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import LoginBox from "./components/auth/LoginBox.tsx";

export default function App() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);

    useEffect(() => {
        axios.get("http://localhost:8080/auth", {withCredentials: true})
            .then(() => setIsAuthenticated(true))
            .catch(() => setIsAuthenticated(false));
    }, [])

    if (isAuthenticated === null) {
        return <div className="text-white">Loading...</div>
    }

    return (
        <div className="min-h-screen w-full flex justify-center items-center bg-[#444444]">
            <div className="flex flex-col gap-4 items-center">
                {
                    isAuthenticated ? (
                        <div
                            className="relative h-screen aspect-[9/16] bg-white rounded-xl shadow-lg flex flex-col justify-center items-center">
                            <VideoScreen/>
                        </div>
                    ) : (
                        <LoginBox onLoginSuccess={() => setIsAuthenticated(true)} />
                    )
                }
            </div>
        </div>
    );
}
