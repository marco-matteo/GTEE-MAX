import requests

BASE_URL = "http://localhost:8080"

def test_register():
    url = f"{BASE_URL}/user/register"
    res = requests.post(
        url, 
        json={"username":"sandro","password":"password"}
    )
    assert res.status_code == 200
    data = res.json()
    assert data["username"] == "sandro"
    assert data["password"] != "password"


def test_login():
    user = {"username":"mario","password":"password"}
    register_url = f"{BASE_URL}/user/register"
    _ = requests.post(
        register_url, 
        json=user
    )

    url = f"{BASE_URL}/user/login"
    
    res = requests.post(
        url,
        json=user
    )

    assert res.status_code == 200
    assert res.headers["set-cookie"].startswith("jwt=")
