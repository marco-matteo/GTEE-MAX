import { test, expect } from "@playwright/test";

test("Test scrolling", async ({ page }) => {
    await page.goto("http://localhost:3000");
    await page.getByRole('textbox', { name: 'Username' }).click();
    await page.getByRole('textbox', { name: 'Username' }).fill('D5h6q758eViTo3LRPT0G');
    await page.getByRole('textbox', { name: 'Password' }).click();
    await page.getByRole('textbox', { name: 'Password' }).fill('D5h6q758eViTo3LRPT0G');
    await page.getByRole('button', { name: 'Login' }).click();

    await page.getByRole("button", { name: "Scroll down" }).click();

    const video = page.locator("video");
    await expect(video).toBeVisible();
});
