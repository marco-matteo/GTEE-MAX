import {useState} from "react";
import LoginBox from "./components/auth/LoginBox.tsx";
import RegisterBox from "./components/auth/RegisterBox.tsx";
import {Route, Routes, useNavigate} from "react-router";
import ProtectedRoute from "./components/auth/ProtectedRoute.tsx";
import Home from "./components/Home.tsx";
import ProfileScreen from "./components/ProfileScreen.tsx";
import NotFound from "./components/NotFound.tsx";

export default function App() {
    const navigate = useNavigate();
    const [uploadBoxOpen, setUploadBoxOpen] = useState(false);

    const handleLogout = () => {
        navigate("/login")
        document.cookie = "jwt="
    }

    return (
        <Routes>
            <Route path="/" element={
                <ProtectedRoute>
                    <Home uploadBoxOpen={uploadBoxOpen} setUploadBoxOpen={setUploadBoxOpen}
                          handleLogout={handleLogout}/>
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
