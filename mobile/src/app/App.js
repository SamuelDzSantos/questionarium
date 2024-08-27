import { StyleSheet, Image, View, Text, Button } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { LoginScreen, QRScannerScreen, ResultadoScreen } from './pages';
import { AuthProvider, useAuth } from './context/AuthContext';
import MaterialIcons from 'react-native-vector-icons/MaterialIcons';


export default function App() {
  return (
    <AuthProvider>
      <Layout></Layout>
    </AuthProvider>
  );
}

const Layout = () => {
  const { authState } = useAuth();

  return (
    <NavigationContainer>
      {authState.authenticated ?
        <AuthenticatedNavigator options={styles.header} /> : <UnauthenticatedNavigator options={styles.header} />
      }
    </NavigationContainer>
  );
}

const AuthenticatedNavigator = () => {
  const AuthenticatedStack = createNativeStackNavigator();
  

  return(
  <AuthenticatedStack.Navigator screenOptions={styles.header} initialRouteName='QRScanner'>
    <AuthenticatedStack.Screen name="QRScanner" component={QRScannerScreen}
      options={{
        headerTitle: () => <LogoTitleMin title='Scanner' />,
        headerRight: () => <LogoutButton />
      }}
    />
    <AuthenticatedStack.Screen name="Resultado" component={ResultadoScreen}
      options={{
        headerTitle: () => <LogoTitleMin title='Resultado' />,
        headerRight: () => <LogoutButton />
      }}
    />
  </AuthenticatedStack.Navigator>
  );
}

const UnauthenticatedNavigator = () => {
  const UnauthenticatedStack = createNativeStackNavigator();

  return(
  <UnauthenticatedStack.Navigator screenOptions={styles.header} initialRouteName='Login'>
    <UnauthenticatedStack.Screen name="Login" component={LoginScreen}
      options={{
        headerTitle: () => <LogoTitle />,
        headerTitleAlign: 'center'
      }}
    />
  </UnauthenticatedStack.Navigator>
  );
}

function LogoTitle() {
  return (
    <Image
      style={{ marginTop: 75 }}
      source={require('../assets/logo-questionarium.png')}
    />
  );
}

function LogoTitleMin({ title }) {
  return (
    <View style={{ flexDirection: 'row', alignItems: 'center', justifyContent: 'center' }}>
      <Image
        style={{ width: 65, height: 50, marginRight: 10 }}
        source={require('../assets/logo-min-questionarium.png')}
      />
      <Text style={{ color: '#FFF', fontWeight: 'bold', fontSize: 18, textAlign: 'center' }}>
        {title}
      </Text>
    </View>
  );
}

function LogoutButton(){
  const { onLogout } = useAuth();
  return <MaterialIcons name="logout" size={40} color="#FFF" onPress={onLogout}/>;
}

const styles = StyleSheet.create({
  header: {
    headerStyle: {
      backgroundColor: '#002436',
    },
    headerTitleAlign: 'center',
    headerTitleStyle: {
      color: '#FFF'
    },
    headerTintColor: '#FFF',
    headerShadowVisible: false
  }
})