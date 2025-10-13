import { test, expect } from '@playwright/test';

test('Register Flow', async ({ page }) => {
    await page.goto('http://localhost:3000/');
    await page.getByRole('button', { name: 'Register' }).click();
    await page.getByRole('textbox', { name: 'Username' }).click();
    await page.getByRole('textbox', { name: 'Username' }).fill('D5h6q758eViTo3LRPT0G');
    await page.getByRole('textbox', { name: 'Password' }).click();
    await page.getByRole('textbox', { name: 'Password' }).fill('D5h6q758eViTo3LRPT0G');
    await page.getByRole('button', { name: 'Register' }).click();

    await expect(page.getByRole('button', { name: 'Login' })).toBeVisible();
});