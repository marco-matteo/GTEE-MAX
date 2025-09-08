## Testing Strategy

### Was
#### Was wird getestet?
- Codequalität: verhindert Bugs
- Unit Tests
- Integrations Tests
- End-to-end Tests
##### Funktional
- Videos schauen
- Videos als Favorit markieren
- man kann sich einloggen
- eigenes und Profil von anderen anschauen
- man kann durch scrollen zu einem neuen Video gelangen
##### Nicht-Funktional
- sicheres Login
- stabile Performance
- Desktop kompatibel
- ansprechende User Experience
- SQL Injection verhindern

#### Was ist out of scope?
- low level Frontend Tests

### Wie
#### Welche Testing Levels werden abgedeckt?
- Unit Testing
- Component Tests
- Integrations Tests
- manuelle Tests
- (End-to-end-tests)
#### Welche Testing Methods? (manual, automated, whitebox, blackbox)
- Automatisiert: CI/CD mit Github Pipline
- Manuell: Whitebox Testing innerhalb vom Development Team und Blackbox Testing mit Zielgruppe
#### Welche Tools und Frameworks verwenden?
- Junit
- (Selenium)

### Wo/Wann/Wer
#### Testumgebung
- containerbasierte Integration Tests
- Github Pipline
- JVM
#### Rollen & Verantwortung
- Development Team
#### Eingang und Ausgangs Kriterien (Wo starten mit testen und wo ist testing fertig)
- Eingang: Im selben PR for oder nach Feature Umsetzung werden entsprechende Tests zum Feature geschrieben
- Ausgang: grundlegendste Themenbereiche der App wurden getestet




