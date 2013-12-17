Android: List Provider
======================

###Descrição
Um aplicativo simples de anotações que tem como intuito mostrar a implementação de um <b>ContentProvider</b> e <b>CursorLoader</b> para carregar dados de um BD SQLite de forma assíncrona em um <b>ListView</b> (utilizando CursorAdapter).

![screenshot](http://i.imgur.com/EZSuBdh.png)

###Recursos
####Principais
* <b>ContentProvider</b>: Manipula o acesso a banco de dados estruturados. Inclui todos os métodos básicos de um CRUD: <i>Create, Read, Update</i> e <i>Delete</i>. Geralmente são confundidos como uma forma de compartilhar dados de forma pública entre diferentes aplicações em um mesmo dispositivo, o que de certa forma não está incorreto, porém, também é possível que estes dados permaneçam privados, como é o caso deste aplicativo. ([Documentação Oficial](http://developer.android.com/guide/topics/providers/content-providers.html))
* <b>CursorLoader</b>: Utilizado para carregar os dados fora da UI Thread (ou seja, de forma assíncrona). ([Documentação Oficial](http://developer.android.com/reference/android/content/CursorLoader.html))
* <b>CursorAdapter</b>: Vincula os dados de um Cursor para um ListView. ([Documentação Oficial](http://developer.android.com/reference/android/widget/CursorAdapter.html))

####Outros
* ListView com <i>rows</i> (linhas) personalizadas, incluindo botões e <i>dividers</i> verticais.
* Conversão de datas para milissegundos (armazenadas como int/long no SQLite)

###Bibliotecas utilizadas
Bibliotecas oficiais de compatibilidade ([Informações](http://developer.android.com/tools/support-library/index.html)):
* Android Support Library <b>v4</b>
* Android Support Library <b>v7</b>

###Recursos externos
* Gerador de ícones: http://flaticons.net

Desenvolvido por
================
Rafael Iop 
* <rafaelviero1@gmail.com>
* [Facebook profile](https://www.facebook.com/rafael.iop)

Licença / License
=================

The MIT License (MIT)

Copyright (c) 2013 Rafael Viero Iop

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
SOFTWARE.
