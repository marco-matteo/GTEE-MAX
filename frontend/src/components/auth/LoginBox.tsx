import axios from "axios";
import {useState} from "react";

export default function LoginBox( {onLoginSuccess }: {onLoginSuccess: () => void}) {
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
        <div className="p-6 bg-gray-100 rounded-lg shadow-md w-64 flex flex-col gap-3">
            <input
                type="text"
                placeholder="Username"
                className="border p-2 rounded"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                className="border p-2 rounded"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button className="bg-blue-500 text-white p-2 rounded" onClick={handleLogin}>
                Login
            </button>
        </div>
    )
}