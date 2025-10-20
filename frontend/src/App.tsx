import {useState} from "react";
import LoginBox from "./components/auth/LoginBox.tsx";
import RegisterBox from "./components/auth/RegisterBox.tsx";
import {Route, Routes, useNavigate} from "react-router";
import ProtectedRoute from "./components/auth/ProtectedRoute.tsx";
import Home from "./components/Home.tsx";
import ProfileScreen from "./components/ProfileScreen.tsx";
import NotFound from "./components/NotFound.tsx";
import axios from "axios";
import VideoScreen from "./components/VideoScreen.tsx";

export default function App() {
    const navigate = useNavigate();
    const [uploadBoxOpen, setUploadBoxOpen] = useState(false);

    const handleLogout = () => {
        axios.delete("http://localhost:8080/user/logout", {withCredentials: true})
            .then(() => navigate("/login"))
    }

    return (
        <Routes>
            <Route path="/" element={
                <ProtectedRoute>
                    <Home uploadBoxOpen={uploadBoxOpen} setUploadBoxOpen={setUploadBoxOpen}
                          handleLogout={handleLogout}/>
                </ProtectedRoute>
            }/>
            <Route path="/video" element={
                <ProtectedRoute>
                    <VideoScreen />
                </ProtectedRoute>
            } />
            <Route path="/video/:videoId" element={
                <ProtectedRoute>
                    <VideoScreen />
                </ProtectedRoute>
            }/>
            <Route path="/profile/:username" element={
                <ProtectedRoute>
                    <ProfileScreen />
                </ProtectedRoute>
            }/>
            <Route
                path="/login"
                element={
                    <LoginBox
                        onLoginSuccess={() => navigate("/")}
                        onRegisterClick={() => navigate("/register")}
                    />
                }
            />
            <Route
                path="/register"
                element={
                    <RegisterBox
                        onRegisterSuccess={() => navigate("/login")}
                        onCancelClick={() => navigate("/login")}
                    />
                }
            />
            <Route path="*" element={<NotFound />} />
        </Routes>
    );
}
