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
