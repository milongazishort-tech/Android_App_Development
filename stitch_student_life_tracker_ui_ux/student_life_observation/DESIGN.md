---
name: Student Life Observation
colors:
  surface: '#faf8ff'
  surface-dim: '#d2d9f4'
  surface-bright: '#faf8ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f2f3ff'
  surface-container: '#eaedff'
  surface-container-high: '#e2e7ff'
  surface-container-highest: '#dae2fd'
  on-surface: '#131b2e'
  on-surface-variant: '#444655'
  inverse-surface: '#283044'
  inverse-on-surface: '#eef0ff'
  outline: '#757686'
  outline-variant: '#c5c5d8'
  surface-tint: '#2f4ddf'
  primary: '#173bd0'
  on-primary: '#ffffff'
  primary-container: '#3a57e8'
  on-primary-container: '#e3e5ff'
  inverse-primary: '#bac3ff'
  secondary: '#006c49'
  on-secondary: '#ffffff'
  secondary-container: '#6cf8bb'
  on-secondary-container: '#00714d'
  tertiary: '#704600'
  on-tertiary: '#ffffff'
  tertiary-container: '#915b00'
  on-tertiary-container: '#ffe1c2'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#dee0ff'
  primary-fixed-dim: '#bac3ff'
  on-primary-fixed: '#00105b'
  on-primary-fixed-variant: '#0130c8'
  secondary-fixed: '#6ffbbe'
  secondary-fixed-dim: '#4edea3'
  on-secondary-fixed: '#002113'
  on-secondary-fixed-variant: '#005236'
  tertiary-fixed: '#ffddb8'
  tertiary-fixed-dim: '#ffb95f'
  on-tertiary-fixed: '#2a1700'
  on-tertiary-fixed-variant: '#653e00'
  background: '#faf8ff'
  on-background: '#131b2e'
  surface-variant: '#dae2fd'
typography:
  headline-xl:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '700'
    lineHeight: 32px
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 24px
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 10px
    fontWeight: '700'
    lineHeight: 12px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  margin: 1.25rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style

This design system targets Gen-Z and collegiate students engaging in intentional habit tracking, daily reflection, and holistic self-improvement across structured 30-day transformation sprints. The visual tone balances vibrant, playful motivation with disciplined academic clarity.

### Design Principles
- **Clarity over Clutter:** High-contrast typographic hierarchy and deliberate negative space counteract academic stress. Data inputs, checklists, and analytics are chunked into discrete cards.
- **Empowering Momentum:** Celebrate every completed micro-task using energetic indigo gradients, dynamic progress rings, and gamified streak indicators that trigger subtle positive reinforcement.
- **Supportive Structure:** Soft pastels soften rigorous analytical metrics (completion ratios, discipline scores, daily summaries) to keep self-observation constructive rather than punitive.
- **Modern App-Native Polish:** Employs rounded surfaces, soft-tinted ambient drop shadows, clear tap targets, and streamlined navigation designed for one-handed mobile interactions.

## Colors

The palette establishes an energetic visual presence using an electric royal indigo as the primary anchor, balanced by optimistic gamification accents and soft tinted backgrounds.

### Palette Architecture
- **Primary (`#3A57E8`):** Royal Indigo. Anchors primary actions, header hero cards, active calendar states, and core progress rings. Supported by an energetic gradient variant shifting from `#3A57E8` to `#4F46E5`.
- **Secondary (`#10B981`):** Growth Emerald. Drives task completion checkmarks, positive trajectory trends, and high consistency badges.
- **Tertiary (`#F59E0B`):** Radiant Amber. Highlights current streaks, milestone trophies, alert indicators, and motivational gamification banners.
- **Accent Soft Tints:**
  - Soft Lavender (`#EEF2FF`): Primary card surfaces and category chips.
  - Soft Mint (`#ECFDF5`): Success and completed task cards.
  - Soft Peach (`#FFF7ED`): High-priority focus alerts.
- **Neutrals:**
  - Background Canvas: Pure White (`#FFFFFF`) with secondary canvas in ultra-light slate (`#F8FAFC`).
  - Surface Neutral: Card containers use `#FFFFFF` on `#F8FAFC` backgrounds, bounded by low-opacity slate borders (`#E2E8F0`).
  - Text Hierarchy: Deep Navy Slate (`#0F172A`) for primary headings, Slate Grey (`#64748B`) for body metadata and secondary descriptors, and Light Slate (`#94A3B8`) for input placeholders and inactive tab icons.

## Typography

The typography leverages **Plus Jakarta Sans** across all roles to project a friendly, approachable, yet geometric and modern aesthetic well-suited for collegiate mobile UI.

### Type Application
- **Display & Large Headlines (`headline-xl`, `headline-lg`):** Reserved for welcome screens, 30-day celebration milestones, and view headers. Set in Bold weight with tight letter spacing (-0.02em) to command attention without feeling sterile.
- **Section & Card Titles (`headline-md`, `headline-sm`):** Provides rapid scanning of metrics, day counters (e.g., "Day 7 / 30"), and card groupings.
- **Body Text (`body-lg`, `body-md`, `body-sm`):** Set with comfortable line-heights for task labels, daily reflection notes, and predictive insight paragraphs.
- **Labels & Tags (`label-lg`, `label-md`, `label-sm`):** Semi-bold and bold treatments for filter chips, bottom-bar tab navigation, status pills, and numeric badge indicators.

## Layout & Spacing

A rhythmic 4px/8px-based spatial model tailored specifically for handheld touchscreen ergonomic interaction.

### Spatial Structure
- **Screen Margins:** Fixed `1.25rem` (20px) on mobile viewports ensures visual stability along screen edges, preventing touch occlusion around curved device bezels.
- **Internal Column Grid:** Standard mobile layouts use a 4-column flexible grid with `1rem` (16px) gutters. Stat summary tiles, calendar heatmaps, and category blocks span 1, 2, or 4 columns cleanly.
- **Vertical Flow:**
  - Component Internal Padding: `space-sm` (8px) for badges and buttons; `space-md` (16px) for standard metric and checklist cards.
  - Section Stacking: `space-lg` (24px) separating distinct analytical or actionable dashboard modules.
  - Screen Footer Safe Areas: Accommodate fixed floating action buttons and the bottom tab bar with `space-xl` bottom padding.

## Elevation & Depth

Visual depth is achieved through layered tonal surfaces and ultra-soft indigo-tinted ambient drop shadows, maintaining an airy, luminous surface profile without muddy dark shadows.

### Elevation Hierarchy
- **Level 0 (Canvas):** Pure off-white background (`#F8FAFC`). Flat with no elevation.
- **Level 1 (Surface Cards & Tiles):** White cards (`#FFFFFF`) with a delicate 1px border of `#E2E8F0` and an ambient glow:
  - `box-shadow: 0 4px 20px -2px rgba(58, 87, 232, 0.05);`
- **Level 2 (Active Elements & Hero Cards):** Solid or gradient primary containers (such as the "Day 7 / 30" banner):
  - `box-shadow: 0 10px 25px -4px rgba(58, 87, 232, 0.25);`
- **Level 3 (Floating Action & Modals):** Central navigation FAB ("+") and dialog overlays:
  - `box-shadow: 0 16px 32px -6px rgba(58, 87, 232, 0.35);`
- **Tonal Inset Depth:** Trackers and unfilled progress bar tracks use subtle inset shading (`background: #EEF2F6`) to create recessed grooves for active fills.

## Shapes

The design system adopts a rounded, student-friendly geometry (`roundedness: 2`) that feels modern, tactile, and approachable.

### Shape Scale Rules
- **Base Components (`rounded`: 0.5rem / 8px):** Checkbox boxes, tiny progress bar caps, and input interior elements.
- **Cards & Dialogs (`rounded-lg`: 1rem / 16px):** Metric scorecards, calendar month containers, task row items, and analytical charts.
- **Feature Modules (`rounded-xl`: 1.5rem / 24px):** Hero dashboard banners, celebratory modal containers, and bottom sheet drawers.
- **Pill Maximums (`rounded-full`: 9999px):** Category badges, streak counter bubbles, status indicators, and all primary call-to-action buttons.

## Components

### Buttons
- **Primary CTA:** Full-width pill (`rounded-full`), solid `#3A57E8` or vibrant gradient `#3A57E8` to `#4F46E5`, white `label-lg` text, height of 52px for immediate thumb-reach. Active state delivers a scale transition (`scale(0.98)`).
- **Secondary / Ghost:** Outlined pill with a 1.5px border in `#3A57E8` or soft tinted fill (`#EEF2FF`), text in `#3A57E8`.
- **Floating Action Button (FAB):** Centered within the bottom navigation bar; 56px circular indigo button housing a bold white plus icon, elevated with Level 3 depth.

### Chips & Badges
- **Category Filter Chips:** Pill-shaped capsules (`rounded-full`) with light neutral backgrounds (`#F1F5F9`) transitioning to solid `#3A57E8` with white text when selected.
- **Streak & Score Badges:** Warm tinted pills (`#FEF3C7`) containing tertiary flame/star icons alongside bold text (`#B45309`).

### Task Checklists
- **List Items:** Enclosed within standalone Level 1 white rounded cards (height: 56px). Features an aligned sequence number ("01. Early Morning Utha"), clear body typography, and an interactive checkbox aligned to the right edge.
- **Checkboxes:** 22px rounded squares (`rounded: 6px`). Unchecked: 1.5px border in `#CBD5E1`. Checked: Filled with `#3A57E8` featuring a crisp white centered check icon.

### Form Inputs
- **Text Fields:** 50px height, rounded corners (`rounded-lg`: 12px), background `#F8FAFC`, border `#E2E8F0`. Focus state shifts border to 2px solid `#3A57E8` with a soft blue outer glow. Includes leading category or auth icons in muted `#94A3B8`.

### Progress Rings & Visual Analytics
- **Circular Progress Indicator:** Concentric SVG stroke indicator with a 10px track width. Background track `#EEF2FF`, filled path `#3A57E8`, centered percentage counter (`headline-lg`) and motivational label.
- **Linear Progress Bars:** 8px height with fully rounded pill caps. Inset grey track filled smoothly with emerald or indigo progression fills.

### Bottom Navigation Bar
- Fixed 64px dock featuring 5 targets: Home, Calendar, Central FAB, Progress, Profile. Active tab renders with solid `#3A57E8` icon and label; inactive tabs render in `#94A3B8`.