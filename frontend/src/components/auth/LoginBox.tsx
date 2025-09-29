import axios from "axios";
import {useState} from "react";

export default function LoginBox( {onLoginSuccess, onRegisterClick}: {onLoginSuccess: () => void, onRegisterClick: () => void}) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        try {
            await axios.post("http://localhost:8080/user/login", {username, password}, {withCredentials: true})
            onLoginSuccess();
        } catch (error) {
            alert("Login failed");
            console.error(error);
        }
    }

    return (
        <div className="flex flex-col gap-5 bg-white px-24 py-8 rounded-2xl">
            <h1 className="text-3xl mx-auto font-bold">Login</h1>
            <input
                type="text"
                placeholder="Username"
                className="border p-5 text-lg rounded-xl"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
            />
            <input
                type="password"
                placeholder="Password"
                className="border p-5 text-lg rounded-xl"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />
            <button className="py-2 rounded-xl bg-purple-500 text-xl font-bold text-white" onClick={handleLogin}>
                Login
            </button>
            <button className="py-2 rounded-xl bg-gray-300 text-xl font-semibold" onClick={onRegisterClick}>
                Register
            </button>
        </div>
    )
}