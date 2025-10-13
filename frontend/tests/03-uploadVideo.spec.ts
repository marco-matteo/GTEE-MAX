import {test} from '@playwright/test';

test('Upload Video Test', async ({ page }) => {
    await page.goto('http://localhost:3000/');
    await page.getByRole('textbox', { name: 'Username' }).click();
    await page.getByRole('textbox', { name: 'Username' }).fill('D5h6q758eViTo3LRPT0G');
    await page.getByRole('textbox', { name: 'Password' }).click();
    await page.getByRole('textbox', { name: 'Password' }).fill('D5h6q758eViTo3LRPT0G');
    await page.getByRole('button', { name: 'Login' }).click();
    await page.getByRole('button', { name: 'Upload Video' }).click();
    await page.getByRole('button', { name: 'Choose File' }).click();
    await page.getByRole('button', { name: 'Choose File' }).setInputFiles('../gt-vids/example.mp4');
    await page.getByRole('textbox', { name: 'Caption' }).click();
    await page.getByRole('textbox', { name: 'Caption' }).fill('sad dog video 🥺');
    await page.getByRole('button', { name: 'Upload', exact: true }).click();
});