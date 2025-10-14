import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router";
import axios from "axios";

export default function ProtectedRoute({ children }: { children: React.ReactNode}) {
    const navigate = useNavigate()
    const [isLoading, setIsLoading] = useState<boolean>(true)
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false)

    useEffect(() => {
        axios.get("http://localhost:8080/auth", {withCredentials: true})
            .then(() => setIsAuthenticated(true))
            .catch(() => navigate('/login'))
            .finally(() => setIsLoading(false))
    }, [navigate])

    if (isLoading) return <div>Loading...</div>

    return isAuthenticated ? children : null
}