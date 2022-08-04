import 'primevue/resources/primevue.min.css';
import 'primeflex/primeflex.css';
import 'primeicons/primeicons.css';
import 'prismjs/themes/prism-coy.css';
import './assets/styles/layout.scss';
import './assets/demo/flags/flags.css';

import { createApp, reactive } from 'vue';
import axios from 'axios'
import VueAxios from 'vue-axios'
import router from './router';
import AppWrapper from './AppWrapper.vue';
import PrimeVue from 'primevue/config';
import AutoComplete from 'primevue/autocomplete';
import Badge from 'primevue/badge';
import BadgeDirective from 'primevue/badgedirective';
import Button from 'primevue/button';
import Calendar from 'primevue/calendar';
import Card from 'primevue/card';
import Checkbox from 'primevue/checkbox';
import Column from 'primevue/column';
import DataTable from 'primevue/datatable';
import Dialog from 'primevue/dialog';
import Dropdown from 'primevue/dropdown';
import Fieldset from 'primevue/fieldset';
import InputMask from 'primevue/inputmask';
import InputNumber from 'primevue/inputnumber';
import InputText from 'primevue/inputtext';
import Paginator from 'primevue/paginator';
import Panel from 'primevue/panel';
import Password from 'primevue/password';
import RadioButton from 'primevue/radiobutton';
import Ripple from 'primevue/ripple';
import SelectButton from 'primevue/selectbutton';
import StyleClass from 'primevue/styleclass';
import Textarea from 'primevue/textarea';
import Toast from 'primevue/toast';
import ToastService from 'primevue/toastservice';
import Toolbar from 'primevue/toolbar';

import CodeHighlight from './AppCodeHighlight';
import { Amplify, I18n, Auth } from 'aws-amplify';
import awsExports from './aws-exports';
import { translations } from '@aws-amplify/ui-vue';

if(process.env.NODE_ENV == "development") {
    axios.defaults.baseURL = "http://localhost:5000";
} else {
    axios.defaults.baseURL = "https://api.serathiuk.dev"
}

axios.interceptors.request.use(function (config) {
    return Auth.currentSession()
        .then(session => {
            // User is logged in. Set auth header on all requests
            config.headers.Authorization = 'Bearer '+session.accessToken.jwtToken
            return Promise.resolve(config)
        })
        .catch(() => {
            // No logged-in user: don't set auth header
            return Promise.resolve(config)
        })
    });

I18n.putVocabularies(translations);
I18n.setLanguage('pt');
Amplify.configure(awsExports);

const app = createApp(AppWrapper);
app.use(VueAxios, axios);
app.config.globalProperties.$appState = reactive({ theme: 'lara-light-indigo', darkTheme: false });

app.use(PrimeVue, { ripple: true, inputStyle: 'outlined' });
app.use(router);
app.use(ToastService);

app.directive('badge', BadgeDirective);
app.directive('ripple', Ripple);
app.directive('code', CodeHighlight);
app.directive('styleclass', StyleClass);

app.component('AutoComplete', AutoComplete);
app.component('Badge', Badge);
app.component('Button', Button);
app.component('Calendar', Calendar);
app.component('Card', Card);
app.component('Checkbox', Checkbox);
app.component('Column', Column);
app.component('DataTable', DataTable);
app.component('DataView', DataView);
app.component('Dialog', Dialog);
app.component('Dropdown', Dropdown);
app.component('Fieldset', Fieldset);
app.component('InputMask', InputMask);
app.component('InputNumber', InputNumber);
app.component('InputText', InputText);
app.component('Paginator', Paginator);
app.component('Panel', Panel);
app.component('Password', Password);
app.component('RadioButton', RadioButton);
app.component('SelectButton', SelectButton);
app.component('Textarea', Textarea);
app.component('Toast', Toast);
app.component('Toolbar', Toolbar);

app.mount('#app');