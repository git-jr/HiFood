<h1 align="center">
<img src ="https://user-images.githubusercontent.com/35709152/182004252-ec7d5e8e-ae4b-4dcf-b4d6-86bb2d8952d2.png" width = 720/>
 </h1>
  
<h1 align="center">
  <img src="https://user-images.githubusercontent.com/35709152/184732373-03dfd5fe-ec9c-4852-955d-a9c45d9ee153.png" alt = "HiFood tela inicial" width="150">
  <img src="https://user-images.githubusercontent.com/35709152/184732367-cc6b6961-d8b9-4bd8-87ab-229b28f3d30b.png" alt = "HiFood tela detalhes" width="150">
  <img src="https://user-images.githubusercontent.com/35709152/184732361-1b8671a4-24e8-4c26-8ec4-acca39679e21.png" alt = "HiFood tela alterar produto" width="150">
  <img src="https://user-images.githubusercontent.com/35709152/184732358-d35f2bdb-7966-43a3-886e-38970b67b560.png" alt = "HiFood dialog cadastro" width="150"> 
</h1>

## HiFood! 

O HiFood é um antigo projeto de estudo para Android com Kotlin, layouts e ViewBinding, baseado em cursos da Alura. No entanto, fui adicionando outras tecnologias e funcionalidades a ele.

Alguns assuntos em estudo nesse projeto:
- [x] Layouts
- [x] View Binding
- [x] Higher order function
- [x] Banco de dados - Room 
- [x] Operações Assíncronas - Coroutines e Flow

- [x] Integração com Web API - Retrofit e banco de dados não relacional do Firebase
- [x] MVVM (ViewModel)
- [x] LiveData com Flow

- [x] Adicionar cores dinâmicas do Material You
- [ ] 🚧 Migrar o sistema de views para Jetpack Compose 
- [ ] 🚧 Adicionar Material Design 3 e cores dinâmicas ao Compose


### Cores dinâmicas
- A ideia original nessa etapa era adicionar as cores dinâmicas com base no wallpaper do dispositivo em atual (mas isso só funciona para o Android 12 ou superior) **entãoooo**, graças a [Palette](https://developer.android.com/jetpack/androidx/releases/palette), (lib oficial do Android e não a cidade do Ash), e a sua compatibilidade incrível com os themas do Jetpack Compose, vamos mudar um pouco as coisas, aqui está uma pequena amostra:


https://github.com/git-jr/HiFood/assets/35709152/a07ec349-38c9-43bb-991b-e0f0d8263eea



Com base nas imagens dos banners do app uma paleta de cores é gerada, essas cores são passadas para o arquivo [padrão de temas do Compose](https://github.com/git-jr/HiFood/blob/master/app/src/main/java/com/paradoxo/hifood/ui/activity/ui/theme/Theme.kt) de lá eu passo as cores são só pra UI dos Composables que eu quiser mas também para as barras do sistema de maneira muito simples e automática.



Aliás, eu mesmo fiz essas imagens com ajuda dos [Fluent Emoji](https://github.com/microsoft/fluentui-emoji) da Microsoft, esses emojis também estão em uso em outros pontos dentro do app.


    Copyright (c) Fluent Emoji Microsoft Corporation.

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE

![]()
![]()
