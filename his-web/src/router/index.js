import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login/index.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    component: () => import('../views/layout/index.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('../views/dashboard/index.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'system/user',
        name: 'UserManage',
        component: () => import('../views/system/user.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'system/role',
        name: 'RoleManage',
        component: () => import('../views/system/role.vue'),
        meta: { title: '角色管理' }
      },
      {
        path: 'system/dept',
        name: 'DeptManage',
        component: () => import('../views/system/dept.vue'),
        meta: { title: '科室管理' }
      },
      {
        path: 'system/dict',
        name: 'DictManage',
        component: () => import('../views/system/dict.vue'),
        meta: { title: '字典管理' }
      },
      {
        path: 'outpatient/register',
        name: 'Register',
        component: () => import('../views/outpatient/register.vue'),
        meta: { title: '门诊挂号' }
      },
      {
        path: 'outpatient/records',
        name: 'RegisterRecords',
        component: () => import('../views/outpatient/records.vue'),
        meta: { title: '挂号记录' }
      },
      {
        path: 'outpatient/patient',
        name: 'PatientManage',
        component: () => import('../views/outpatient/patient.vue'),
        meta: { title: '患者管理' }
      },
      {
        path: 'outpatient/schedule',
        name: 'ScheduleManage',
        component: () => import('../views/outpatient/schedule.vue'),
        meta: { title: '医生排班' }
      },
      {
        path: 'doctor/workstation',
        name: 'DoctorWorkstation',
        component: () => import('../views/doctor/workstation.vue'),
        meta: { title: '医生工作站' }
      },
      {
        path: 'charge/list',
        name: 'ChargeList',
        component: () => import('../views/charge/list.vue'),
        meta: { title: '门诊收费' }
      },
      {
        path: 'charge/records',
        name: 'ChargeRecords',
        component: () => import('../views/charge/records.vue'),
        meta: { title: '收费记录' }
      },
      {
        path: 'pharmacy/dispensing',
        name: 'Dispensing',
        component: () => import('../views/pharmacy/dispensing.vue'),
        meta: { title: '药房发药' }
      },
      {
        path: 'pharmacy/drug',
        name: 'DrugManage',
        component: () => import('../views/pharmacy/drug.vue'),
        meta: { title: '药品管理' }
      },
      {
        path: 'pharmacy/stock',
        name: 'StockManage',
        component: () => import('../views/pharmacy/stock.vue'),
        meta: { title: '库存管理' }
      },
      // 财务系统
      {
        path: 'finance/income',
        name: 'FinanceIncome',
        component: () => import('../views/finance/income.vue'),
        meta: { title: '收入管理' }
      },
      {
        path: 'finance/expense',
        name: 'FinanceExpense',
        component: () => import('../views/finance/expense.vue'),
        meta: { title: '支出管理' }
      },
      {
        path: 'finance/report',
        name: 'FinanceReport',
        component: () => import('../views/finance/report.vue'),
        meta: { title: '财务报表' }
      },
      // 人事系统
      {
        path: 'hr/employee',
        name: 'HrEmployee',
        component: () => import('../views/hr/employee.vue'),
        meta: { title: '员工档案' }
      },
      {
        path: 'hr/attendance',
        name: 'HrAttendance',
        component: () => import('../views/hr/attendance.vue'),
        meta: { title: '考勤管理' }
      },
      {
        path: 'hr/salary',
        name: 'HrSalary',
        component: () => import('../views/hr/salary.vue'),
        meta: { title: '薪资管理' }
      },
      // 绩效考核
      {
        path: 'performance/template',
        name: 'PerfTemplate',
        component: () => import('../views/performance/template.vue'),
        meta: { title: '考核模板' }
      },
      {
        path: 'performance/evaluate',
        name: 'PerfEvaluate',
        component: () => import('../views/performance/evaluate.vue'),
        meta: { title: '绩效考核' }
      },
      {
        path: 'performance/result',
        name: 'PerfResult',
        component: () => import('../views/performance/result.vue'),
        meta: { title: '考核结果' }
      },
      // 职称评价
      {
        path: 'title/apply',
        name: 'TitleApply',
        component: () => import('../views/title/apply.vue'),
        meta: { title: '职称申请' }
      },
      {
        path: 'title/review',
        name: 'TitleReview',
        component: () => import('../views/title/review.vue'),
        meta: { title: '职称评审' }
      },
      {
        path: 'title/manage',
        name: 'TitleManage',
        component: () => import('../views/title/manage.vue'),
        meta: { title: '职称管理' }
      },
      // 科研管理
      {
        path: 'research/project',
        name: 'ResearchProject',
        component: () => import('../views/research/project.vue'),
        meta: { title: '课题管理' }
      },
      {
        path: 'research/paper',
        name: 'ResearchPaper',
        component: () => import('../views/research/paper.vue'),
        meta: { title: '论文管理' }
      },
      {
        path: 'research/achievement',
        name: 'ResearchAchievement',
        component: () => import('../views/research/achievement.vue'),
        meta: { title: '成果管理' }
      },
      // 不良事件
      {
        path: 'adverse/report',
        name: 'AdverseReport',
        component: () => import('../views/adverse/report.vue'),
        meta: { title: '事件上报' }
      },
      {
        path: 'adverse/handle',
        name: 'AdverseHandle',
        component: () => import('../views/adverse/handle.vue'),
        meta: { title: '事件处理' }
      },
      {
        path: 'adverse/statistics',
        name: 'AdverseStatistics',
        component: () => import('../views/adverse/statistics.vue'),
        meta: { title: '事件统计' }
      },
      // 院长驾驶舱
      {
        path: 'bigscreen/dashboard',
        name: 'BigScreenDashboard',
        component: () => import('../views/bigscreen/dashboard.vue'),
        meta: { title: '院长驾驶舱' }
      },
      // 预约挂号
      {
        path: 'outpatient/appointment',
        name: 'Appointment',
        component: () => import('../views/outpatient/appointment.vue'),
        meta: { title: '预约挂号' }
      },
      {
        path: 'outpatient/queue',
        name: 'Queue',
        component: () => import('../views/outpatient/queue.vue'),
        meta: { title: '排队叫号' }
      },
      // 电子病历
      {
        path: 'emr/record',
        name: 'EmrRecord',
        component: () => import('../views/emr/record.vue'),
        meta: { title: '电子病历' }
      },
      {
        path: 'emr/template',
        name: 'EmrTemplate',
        component: () => import('../views/emr/template.vue'),
        meta: { title: '病历模板' }
      },
      {
        path: 'emr/quality',
        name: 'EmrQuality',
        component: () => import('../views/emr/quality.vue'),
        meta: { title: '病历质控' }
      },
      // 设备管理
      {
        path: 'equipment/list',
        name: 'EquipmentList',
        component: () => import('../views/equipment/list.vue'),
        meta: { title: '设备台账' }
      },
      {
        path: 'equipment/maintenance',
        name: 'EquipmentMaintenance',
        component: () => import('../views/equipment/maintenance.vue'),
        meta: { title: '设备维修' }
      },
      {
        path: 'equipment/depreciation',
        name: 'EquipmentDepreciation',
        component: () => import('../views/equipment/depreciation.vue'),
        meta: { title: '设备折旧' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    next()
  } else if (!token) {
    next('/login')
  } else {
    next()
  }
})

export default router
