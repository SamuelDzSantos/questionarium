import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { CadastroComponent } from './components/cadastro/cadastro.component';
import { EdicaoPerfilComponent } from './components/edicao-perfil/edicao-perfil.component';
import { defaultCanActivateGuard } from './guards/default-can-activate.guard';
//import { authResolver } from './interceptors/resolver';
import { MainComponent } from './components/main/main.component';

export const routes: Routes = [
    {path:"",component:MainComponent,//resolve:{user:authResolver},
        children:[
            {path:"",redirectTo:"/home",pathMatch:"full"},
            {path:"login",component:LoginComponent},
            {path:"home",component:HomeComponent},
            {path:"cadastro",component:CadastroComponent},
            {path:"edicaoPerfil",component:EdicaoPerfilComponent},
            {path:"**",redirectTo:"/home",pathMatch:"full"}
        ],
        canActivateChild:[defaultCanActivateGuard]
    }
];